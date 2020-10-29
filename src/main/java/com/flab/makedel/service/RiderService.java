package com.flab.makedel.service;

import com.flab.makedel.dao.DeliveryDAO;
import com.flab.makedel.dto.OrderDTO.OrderStatus;
import com.flab.makedel.dto.RiderDTO;
import com.flab.makedel.mapper.OrderMapper;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RiderService {

    private final DeliveryDAO deliveryDAO;
    private final OrderMapper orderMapper;

    public void registerStandbyRiderWhenStartWork(RiderDTO rider) {
        deliveryDAO.insertStandbyRiderWhenStartWork(rider);
    }

    public void deleteStandbyRiderWhenStopWork(RiderDTO rider) {
        deliveryDAO.deleteStandbyRiderWhenStopWork(rider);
    }

    @Transactional
    public void acceptStandbyOrder(long orderId, RiderDTO rider) {
        deliveryDAO.updateStandbyOrderToDelivering(orderId, rider);
        orderMapper.updateStandbyOrderToDelivering(orderId, rider.getId(), OrderStatus.DELIVERING);
    }

    @Transactional
    public void finishDeliveringOrder(long orderId, RiderDTO rider) {
        orderMapper.finishDeliveringOrder(orderId, OrderStatus.COMPLETE_DELIVERY);
        deliveryDAO.insertStandbyRiderWhenStartWork(rider);
    }

}
