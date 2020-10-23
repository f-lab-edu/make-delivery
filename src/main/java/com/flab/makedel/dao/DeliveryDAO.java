package com.flab.makedel.dao;

import com.flab.makedel.dto.RiderDTO;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryDAO {

    private final RedisTemplate<String, RiderDTO> redisTemplate;
    private static final String riderKey = "STANDBY_RIDERS";
    private static final String deliveryOrderKey = "STANDBY_ORDERS";


    public void insertStandbyRider(RiderDTO rider) {
        redisTemplate.opsForHash().put(riderKey, rider.getId(), rider);
    }

    public void deleteStandbyRider(RiderDTO rider) {
        redisTemplate.opsForHash().delete(riderKey, rider.getId());
    }

    public RiderDTO selectStandbyRider(String riderId) {
        return (RiderDTO) redisTemplate.opsForHash().get(riderKey, riderId);
    }

    public Set<Object> selectStandbyRiderList() {
        return redisTemplate.opsForHash().keys(riderKey);
    }
}
