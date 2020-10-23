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

    public void registerStandbyRider(RiderDTO rider) {
        deliveryDAO.insertStandbyRider(rider);
    }

    public void deleteStandbyRider(RiderDTO rider) {
        deliveryDAO.deleteStandbyRider(rider);
    }

    public RiderDTO loadStandbyRiderInfo(String riderId) {
        return deliveryDAO.selectStandbyRider(riderId);
    }

    public Set<Object> loadStandbyRiderList() {
        return deliveryDAO.selectStandbyRiderList();
    }

}
