package com.flab.makedel.service;

import com.flab.makedel.dao.DeliveryDAO;
import com.flab.makedel.dto.OrderReceiptDTO;
import com.flab.makedel.dto.RiderDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryDAO deliveryDAO;

    public void registerStandbyOrderWhenOrderApprove(long orderId, OrderReceiptDTO orderReceipt) {
        deliveryDAO.insertStandbyOrderWhenOrderApprove(orderId, orderReceipt);
    }

    public OrderReceiptDTO loadStandbyOrder(long orderId, String riderAddress) {
        return deliveryDAO.selectStandbyOrder(orderId, riderAddress);
    }

    public List<String> loadStandbyOrderList(String riderAddress) {
        return deliveryDAO.selectStandbyOrderList(riderAddress);
    }

}
