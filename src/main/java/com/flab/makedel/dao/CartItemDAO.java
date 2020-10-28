package com.flab.makedel.dao;

import com.flab.makedel.dto.CartItemDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

/*
    레디스에서의 트랜잭션은 RDB의 트랜잭션과는 다르다. 레디스의 트랜잭션은 rollback기능이 없다.
    레디스 트랜잭션에서의 오류는 잘못된 명령어나 데이터타입을 실수로 쓸 때만 나기 때문에 롤백을 전부다 하지않고
    exec 후에 오류가 나지 않은 부분은 실행된다.
    exec 이전에 command queue에 적재하는 도중 실패하는 경우 (command 문법오류,메모리 부족오류등,
    다른 클라이언트에서 command날려 atomic보장이 안되는 경우) 에는 exec하면 전부 discard된다.
    (실험해보니 multi 후 트랜잭션중 다른 스레드에서 command를 날리면 discard된다.)
    (레디스 2.6.5이후로 트랜잭션시작 후 오류가 있으면 exec될 때 전부 discard된다.)
    트랜잭션 명령어들은 exec되기 위해 큐에서 기다리는데 discard를 이용해 실행을 하지 않을 수 있다.
    트랜잭션의 locking은 watch를 이용한 optimistic locking이다. watch로 어떠한 키를 감시하고
    이 키의 트랜잭션이 multi로 시작되기전에 watch할 때의 값과 multi할 때의 값이 변경이 없어야지
    트랜잭션이 시작할 수 있다. 만약에 이 값이 변경이 되었다면 race condition이 일어난 것이기 때문에
    트랜잭션 에러가 난다.
 */

@Repository
@RequiredArgsConstructor
public class CartItemDAO {

    private final RedisTemplate<String, CartItemDTO> redisTemplate;
    private static final String cartKey = ":CART";

    public static String generateCartKey(String id) {
        return id + cartKey;
    }

    public List<CartItemDTO> selectCartList(String userId) {

        final String key = generateCartKey(userId);

        List<CartItemDTO> cartList = redisTemplate
            .opsForList()
            .range(key, 0, -1);

        return cartList;

    }

    public List<CartItemDTO> getCartAndDelete(String userId) {

        final String key = generateCartKey(userId);

        List<Object> cartListObject = redisTemplate.execute(
            new SessionCallback<List<Object>>() {
                @Override
                public List<Object> execute(RedisOperations redisOperations)
                    throws DataAccessException {
                    try {
                        redisOperations.watch(key);
                        redisOperations.multi();
                        redisOperations.opsForList().range(key, 0, -1);
                        redisOperations.delete(key);
                        return redisOperations.exec();
                    } catch (Exception exception) {
                        redisOperations.discard();
                        throw exception;
                    }

                }
            }
        );

        List<CartItemDTO> cartList = (List<CartItemDTO>) cartListObject.get(0);

        return cartList;
    }

    /*
        레디스 서버에 반복문을 돌며 여러번 리스트의 원소를 push한다면 RTT때문에 오버헤드가 생깁니다.
        따라서 레디스 서버에 요청을 보낼때 한번에 여러 원소들을 보내야합니다.
        MySQL에서는 이러한 기능을 위해 bulk insert를 지원하지
        레디스에서는 bulk(다중) insert가 따로 존재하지 않습니다.
        따라서 레디스에서 지원해주는 pipeline api인 executePipelined 메소드를 이용해
        레디스에 연결을 한후 모든 원소들을 push한 뒤 연결을 닫습니다.
     */

    public void insertMenuList(String userId, List<CartItemDTO> cartList) {
        final String key = generateCartKey(userId);

        RedisSerializer keySerializer = redisTemplate.getStringSerializer();
        RedisSerializer valueSerializer = redisTemplate.getValueSerializer();

        redisTemplate.executePipelined((RedisCallback<Object>) RedisConnection -> {
            cartList.forEach(cart -> {
                RedisConnection.rPush(keySerializer.serialize(key),
                    valueSerializer.serialize(cart));
            });
            return null;
        });
    }

    public void insertMenu(String userId, CartItemDTO cart) {
        final String key = generateCartKey(userId);
        redisTemplate.opsForList().rightPush(key, cart);
    }

    public void deleteMenuList(String userId) {
        final String key = generateCartKey(userId);
        redisTemplate.delete(key);
    }

}
