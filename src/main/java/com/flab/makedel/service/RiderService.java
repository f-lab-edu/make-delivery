package com.flab.makedel.service;

import com.flab.makedel.dao.DeliveryDAO;
import com.flab.makedel.dto.OrderDTO.OrderStatus;
import com.flab.makedel.dto.PushMessageDTO;
import com.flab.makedel.dto.RiderDTO;
import com.flab.makedel.mapper.OrderMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RiderService {

    private final DeliveryDAO deliveryDAO;
    private final OrderMapper orderMapper;
    private final PushService pushService;

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

    public void sendMessageToStandbyRidersInSameArea(String address, PushMessageDTO pushMessage) {
        Set<String> tokenSet = deliveryDAO.selectStandbyRiderTokenList(address);
        List<Message> messages = tokenSet.stream().map(token -> Message.builder()
            .putData("title", pushMessage.getTitle())
            .putData("content", pushMessage.getContent())
            .putData("orderReceipt", pushMessage.getOrderReceipt().toString())
            .putData("createdAt", pushMessage.getCreatedAt())
            .setToken(token)
            .build())
            .collect(Collectors.toList());

        pushService.sendMessages(messages);
    }

}
