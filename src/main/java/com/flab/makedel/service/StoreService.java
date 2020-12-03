package com.flab.makedel.service;

import com.flab.makedel.Exception.DuplicatedIdException;
import com.flab.makedel.dto.OrderDTO.OrderStatus;
import com.flab.makedel.dto.OrderDetailDTO;
import com.flab.makedel.dto.OrderReceiptDTO;
import com.flab.makedel.dto.PushMessageDTO;
import com.flab.makedel.dto.StoreDTO;
import com.flab.makedel.mapper.OrderMapper;
import com.flab.makedel.mapper.StoreMapper;
import com.google.firebase.messaging.FirebaseMessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreMapper storeMapper;
    private final OrderMapper orderMapper;
    private final DeliveryService deliveryService;
    private final RiderService riderService;

    public void insertStore(StoreDTO store, String ownerId) {
        try {
            StoreDTO newStore = setOwnerID(store, ownerId);
            storeMapper.insertStore(newStore);
        } catch (DuplicateKeyException e) {
            throw new DuplicateKeyException("Same Store Name" + store.getName());
        }
    }

    private StoreDTO setOwnerID(StoreDTO store, String ownerId) {
        StoreDTO newStore = StoreDTO.builder()
            .name(store.getName())
            .phone(store.getPhone())
            .address(store.getAddress())
            .ownerId(ownerId)
            .introduction(store.getIntroduction())
            .categoryId(store.getCategoryId())
            .build();
        return newStore;
    }

    public List<StoreDTO> getMyAllStore(String ownerId) {
        return storeMapper.selectStoreList(ownerId);
    }

    public StoreDTO getMyStore(long storeId, String ownerId) {
        return storeMapper.selectStore(storeId, ownerId);
    }

    private boolean isMyStore(long storeId, String ownerId) {
        return storeMapper.isMyStore(storeId, ownerId);
    }

    public void closeMyStore(long storeId) {
        storeMapper.closeMyStore(storeId);
    }

    public void openMyStore(long storeId) {
        storeMapper.openMyStore(storeId);
    }

    public void validateMyStore(long storeId, String ownerId) throws HttpClientErrorException {
        boolean isMyStore = isMyStore(storeId, ownerId);
        if (!isMyStore) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }

    @Transactional
    public void approveOrder(long orderId) {
        orderMapper.approveOrder(orderId, OrderStatus.APPROVED_ORDER);
        OrderReceiptDTO orderReceipt = orderMapper.selectOrderReceipt(orderId);
        deliveryService.registerStandbyOrderWhenOrderApprove(orderId, orderReceipt);
        riderService.sendMessageToStandbyRidersInSameArea(orderReceipt.getStoreInfo().getAddress(),
            getPushMessage(orderReceipt));
    }

    private PushMessageDTO getPushMessage(OrderReceiptDTO orderReceipt) {
        return PushMessageDTO.builder()
            .title(PushMessageDTO.RIDER_MESSAGE_TITLE)
            .content(PushMessageDTO.RIDER_MESSAGE_TITLE)
            .createdAt(LocalDateTime.now().toString())
            .orderReceipt(orderReceipt)
            .build();

    }

}
