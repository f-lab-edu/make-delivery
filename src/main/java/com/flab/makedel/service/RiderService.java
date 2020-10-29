package com.flab.makedel.service;

import com.flab.makedel.dao.DeliveryDAO;
import com.flab.makedel.dto.RiderDTO;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiderService {

    private final DeliveryDAO deliveryDAO;

    public void registerStandbyRiderWhenStartWork(RiderDTO rider) {
        deliveryDAO.insertStandbyRiderWhenStartWork(rider);
    }

    public void deleteStandbyRiderWhenStopWork(RiderDTO rider) {
        deliveryDAO.deleteStandbyRiderWhenStopWork(rider);
    }

    public void acceptStandbyOrder(long orderId, RiderDTO rider) {
        deliveryDAO.updateStandbyOrderToDelivering(orderId, rider);
    }

}
