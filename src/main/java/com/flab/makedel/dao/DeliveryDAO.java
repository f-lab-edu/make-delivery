package com.flab.makedel.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.makedel.dto.OrderDetailDTO;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryDAO {

    private final RedisTemplate<String, RiderDTO> redisTemplate;
    private final RedisTemplate<String, OrderReceiptDTO> standbyOrderRedisTemplate;
    private static final String riderKey = "STANDBY_RIDERS";
    private static final String deliveryOrderKey = "STANDBY_ORDERS";
    private static final String orderHashKey = ":ORDERS";
    private final ObjectMapper objectMapper;

    private static String generateHashKey(long orderId) {
        return orderId + orderHashKey;
    }

    public void insertStandbyRider(RiderDTO rider) {
        redisTemplate.opsForHash().put(riderKey, rider.getId(), rider);
    }

    public void deleteStandbyRider(RiderDTO rider) {
        redisTemplate.opsForHash().delete(riderKey, rider.getId());
    }

    public RiderDTO selectStandbyRider(String riderId) {
        return (RiderDTO) redisTemplate.opsForHash().get(riderKey, riderId);
    }

    /*
        레디스의 명령어중 keys를 이용하면 모든 키값들을 가져올 수 있지만 이키값들을 가져오는동안
        lock이 걸립니다. 따라서 성능에 영향을 줄 수 있어 레디스에서는 scan,hscan을 권장합니다.
        레디스는 싱글스레드로 동작하기 때문에 이처럼 어떤 명령어를 O(n)시간 동안 수행하면서 lock이
        걸린다면 그시간동안 keys 명령어를 수행하기 위해 멈춰버리기 때문입니다.
     */

    public Set<String> selectStandbyRiderList() {

        Set<String> result = new HashSet<>();

        redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection redisConnection)
                throws DataAccessException {

                ScanOptions options = ScanOptions.scanOptions().match("*").build();
                Cursor<Entry<byte[], byte[]>> entries = redisConnection
                    .hScan(riderKey.getBytes(), options);

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
        standbyOrderRedisTemplate.opsForHash()
            .put(deliveryOrderKey, generateHashKey(orderId), orderReceipt);
    }

    public OrderReceiptDTO selectStandbyOrder(long orderId) {
        return (OrderReceiptDTO) standbyOrderRedisTemplate.opsForHash()
            .get(deliveryOrderKey, generateHashKey(orderId));
    }

    public List<Object> selectStandbyOrderList() {

        List<Object> result = new ArrayList<>();

        redisTemplate.execute(new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection redisConnection)
                throws DataAccessException {

                ScanOptions options = ScanOptions.scanOptions().match("*").build();
                Cursor<Entry<byte[], byte[]>> entries = redisConnection
                    .hScan(deliveryOrderKey.getBytes(), options);

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

}
