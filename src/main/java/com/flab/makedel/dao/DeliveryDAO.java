package com.flab.makedel.dao;

import com.flab.makedel.dto.RiderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryDAO {

    private final RedisTemplate<String, RiderDTO> redisTemplate;
    private static final String riderKey = ":RIDER";

    public static String generateRiderKey(String id) {
        return id + riderKey;
    }

    public void insertStandbyRider(RiderDTO rider) {
        final String key = generateRiderKey(rider.getId());
        redisTemplate.opsForHash().put(key, rider.getId(), rider);
    }


}
