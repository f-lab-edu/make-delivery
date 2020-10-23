package com.flab.makedel.service;

import com.flab.makedel.dao.DeliveryDAO;
import com.flab.makedel.dto.RiderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiderService {

    private final DeliveryDAO deliveryDAO;

    public void registerStandbyRider(RiderDTO rider) {
        deliveryDAO.insertStandbyRider(rider);
    }

}
