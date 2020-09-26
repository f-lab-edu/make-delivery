package com.flab.makedel.dao;

import com.flab.makedel.dto.CartItemDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/*
    레디스에서의 트랜잭션은 RDB의 트랜잭션과는 다르다. 레디스의 트랜잭션은 rollback기능이 없다.
    레디스 트랜잭션에서의 오류는 잘못된 명령어나 데이터타입을 실수로 쓸 때만 나기 때문에 롤백을 전부다 하지않고
    오류가 나지 않은 부분은 실행된다. 롤백이 없기 떄문에 레디스의 트랜잭션은 간단하고 빠르다.
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

    public void insertMenu(String userId, CartItemDTO cart) {
        final String key = generateCartKey(userId);
        redisTemplate.opsForList().rightPush(key, cart);
    }

    public void deleteMenuList(String userId) {
        final String key = generateCartKey(userId);
        redisTemplate.delete(key);
    }

}
