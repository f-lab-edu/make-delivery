package com.flab.makedel.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.makedel.dto.CartItemDTO;
import com.flab.makedel.utils.RedisUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartItemDAO {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public List<CartItemDTO> selectCartList(String userId) {
        final String key = RedisUtil.generateCartKey(userId);
        List<CartItemDTO> cartList = redisTemplate
            .opsForList()
            .range(key, 0, -1)
            .stream()
            .map(cart -> objectMapper.convertValue(cart, CartItemDTO.class))
            .collect(Collectors.toList());

        return cartList;
    }

    public void insertMenu(String userId, CartItemDTO cart) {
        final String key = RedisUtil.generateCartKey(userId);
        redisTemplate.opsForList().rightPush(key, cart);
    }

    public void deleteMenuList(String userId) {
        final String key = RedisUtil.generateCartKey(userId);
        redisTemplate.delete(key);
    }

}
