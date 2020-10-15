package com.flab.makedel.service;

import com.flab.makedel.dao.CartItemDAO;
import com.flab.makedel.dto.CartItemDTO;
import com.flab.makedel.dto.OrderDTO;
import com.flab.makedel.dto.OrderDTO.OrderStatus;
import com.flab.makedel.dto.OrderMenuDTO;
import com.flab.makedel.dto.OrderMenuOptionDTO;
import com.flab.makedel.dto.OrderReceiptDTO;
import com.flab.makedel.dto.StoreInfoDTO;
import com.flab.makedel.dto.PayDTO.PayType;
import com.flab.makedel.dto.UserInfoDTO;
import com.flab.makedel.mapper.OrderMapper;
import com.flab.makedel.mapper.StoreMapper;
import com.flab.makedel.mapper.UserMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final OrderTransactionService orderTransactionService;
    private final CartItemDAO cartItemDAO;
    private final StoreMapper storeMapper;

    @Transactional
    public OrderReceiptDTO registerOrder(String userId, long storeId, PayType payType) {

        UserInfoDTO user = userMapper.selectUserInfo(userId);
        OrderDTO orderDTO = getOrderDTO(user, storeId);
        List<CartItemDTO> cartList;
        List<OrderMenuDTO> orderMenuList = new ArrayList<>();
        List<OrderMenuOptionDTO> orderMenuOptionList = new ArrayList<>();
        OrderReceiptDTO orderReceipt;

        cartList = cartItemDAO.getCartAndDelete(userId);

        orderTransactionService.insertCartListIfRollback(userId, cartList);

        long totalPrice = orderTransactionService
            .order(orderDTO, cartList, orderMenuList, orderMenuOptionList);
        orderTransactionService.pay(payType, totalPrice, orderDTO.getId());
        orderMapper.completeOrder(totalPrice, orderDTO.getId(), OrderStatus.COMPLETE_ORDER);
        orderReceipt = getOrderReceipt(orderDTO, cartList, totalPrice, storeId,
            user);

        return orderReceipt;
    }

    private OrderDTO getOrderDTO(UserInfoDTO userInfo, long storeId) {
        OrderDTO orderDTO = OrderDTO.builder()
            .address(userInfo.getAddress())
            .userId(userInfo.getId())
            .orderStatus(OrderStatus.BEFORE_ORDER)
            .storeId(storeId)
            .build();
        return orderDTO;
    }

    private OrderReceiptDTO getOrderReceipt(OrderDTO orderDTO, List<CartItemDTO> cartList,
        long totalPrice, long storeId, UserInfoDTO userInfo) {

        StoreInfoDTO storeInfo = storeMapper.selectStoreInfo(storeId);
        return OrderReceiptDTO.builder()
            .orderId(orderDTO.getId())
            .userInfo(userInfo)
            .totalPrice(totalPrice)
            .storeInfo(storeInfo)
            .cartList(cartList)
            .build();

    }

}
