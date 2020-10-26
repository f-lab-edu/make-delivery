package com.flab.makedel.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.makedel.dto.OrderReceiptDTO;
import com.flab.makedel.dto.RiderDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryDAO {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String STANDBY_RIDERS_KEY = "STANDBY_RIDERS";
    private static final String STANDBY_ORDERS_KEY = "STANDBY_ORDERS";
    private static final String DELIVERING_RIDERS_KEY = "DELIVERING_RIDERS";
    private static final String DELIVERING_ORDERS_KEY = "DELIVERING_ORDERS";

    private String generateHashKey(long orderId) {
        return String.valueOf(orderId);
    }

    public void insertStandbyRider(RiderDTO rider) {
        redisTemplate.opsForHash().put(STANDBY_RIDERS_KEY, rider.getId(), rider);
    }

    public void deleteStandbyRider(String riderId) {
        redisTemplate.opsForHash().delete(STANDBY_RIDERS_KEY, riderId);
    }

    public RiderDTO selectStandbyRider(String riderId) {
        return (RiderDTO) redisTemplate.opsForHash().get(STANDBY_RIDERS_KEY, riderId);
    }

    /*
        레디스의 명령어중 keys를 이용하면 모든 키값들을 가져올 수 있지만 이키값들을 가져오는동안
        lock이 걸립니다. 따라서 성능에 영향을 줄 수 있어 레디스에서는 scan,hscan을 권장합니다.
        레디스는 싱글스레드로 동작하기 때문에 이처럼 어떤 명령어를 O(n)시간 동안 수행하면서 lock이
        걸린다면 그시간동안 keys 명령어를 수행하기 위해 blocking이 되기 때문입니다.
     */

    public Set<String> selectStandbyRiderList() {

        Set<String> result = new HashSet<>();
        redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection redisConnection)
                throws DataAccessException {

                ScanOptions options = ScanOptions.scanOptions().match("*").count(200).build();
                Cursor<Entry<byte[], byte[]>> entries = redisConnection
                    .hScan(STANDBY_RIDERS_KEY.getBytes(), options);

                while (entries.hasNext()) {
                    Entry<byte[], byte[]> entry = entries.next();
                    byte[] actualKey = entry.getKey();
                    result.add(new String(actualKey));
                }

                return result;
            }
        });
        return result;
    }

    public void insertStandbyOrder(long orderId, OrderReceiptDTO orderReceipt) {
        redisTemplate.opsForHash()
            .put(STANDBY_ORDERS_KEY, generateHashKey(orderId), orderReceipt);
    }

    public OrderReceiptDTO selectStandbyOrder(long orderId) {
        return (OrderReceiptDTO) redisTemplate.opsForHash()
            .get(STANDBY_ORDERS_KEY, generateHashKey(orderId));
    }

    public void deleteStandbyOrder(long orderId) {
        redisTemplate.opsForHash().delete(STANDBY_ORDERS_KEY, generateHashKey(orderId));
    }

    public List<String> selectStandbyOrderList() {

        List<String> result = new ArrayList<>();
        redisTemplate.execute(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(RedisConnection redisConnection)
                throws DataAccessException {

                ScanOptions options = ScanOptions.scanOptions().match("*").count(200).build();
                Cursor<Entry<byte[], byte[]>> entries = redisConnection
                    .hScan(STANDBY_ORDERS_KEY.getBytes(), options);

                while (entries.hasNext()) {
                    Entry<byte[], byte[]> entry = entries.next();
                    byte[] actualKey = entry.getKey();
                    result.add(new String(actualKey));
                }

                return result;
            }
        });
        return result;
    }

    public void updateStandbyOrderAndRiderToDelivering(long orderId, String riderId) {

        redisTemplate.execute(
            new SessionCallback<Object>() {
                @Override
                public Object execute(RedisOperations redisOperations)
                    throws DataAccessException {
                    try {
                        redisOperations.watch(STANDBY_RIDERS_KEY);
                        redisOperations.watch(STANDBY_ORDERS_KEY);
                        redisOperations.watch(DELIVERING_RIDERS_KEY);
                        redisOperations.watch(DELIVERING_ORDERS_KEY);

                        RiderDTO riderDTO = (RiderDTO) redisOperations.opsForHash()
                            .get(STANDBY_RIDERS_KEY, riderId);
                        OrderReceiptDTO orderReceiptDTO = (OrderReceiptDTO) redisOperations
                            .opsForHash()
                            .get(STANDBY_ORDERS_KEY, generateHashKey(orderId));

                        redisOperations.multi();

                        redisOperations.opsForHash().put(DELIVERING_RIDERS_KEY, riderId, riderDTO);
                        redisOperations.opsForHash()
                            .put(DELIVERING_ORDERS_KEY, generateHashKey(orderId), orderReceiptDTO);

                        redisOperations.opsForHash().delete(STANDBY_RIDERS_KEY, riderId);
                        redisOperations.opsForHash()
                            .delete(STANDBY_ORDERS_KEY, generateHashKey(orderId));
                        return redisOperations.exec();
                    } catch (Exception exception) {
                        redisOperations.discard();
                        throw exception;
                    }
                }
            }
        );
    }


}
