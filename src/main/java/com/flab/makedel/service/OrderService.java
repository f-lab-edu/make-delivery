package com.flab.makedel.service;

import com.flab.makedel.dao.CartItemDAO;
import com.flab.makedel.dto.CartItemDTO;
import com.flab.makedel.dto.OrderDTO;
import com.flab.makedel.dto.OrderDTO.OrderStatus;
import com.flab.makedel.dto.OrderMenuDTO;
import com.flab.makedel.dto.OrderMenuOptionDTO;
import com.flab.makedel.dto.PayDTO.PayType;
import com.flab.makedel.dto.UserInfoDTO;
import com.flab.makedel.mapper.OrderMapper;
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

    @Transactional
    public void registerOrder(String userId, long storeId, PayType payType) {

        UserInfoDTO user = userMapper.selectUserInfo(userId);
        OrderDTO orderDTO = makeOrderDTO(user, storeId);
        List<CartItemDTO> cartList = null;
        List<OrderMenuDTO> orderMenuList = new ArrayList<>();
        List<OrderMenuOptionDTO> orderMenuOptionList = new ArrayList<>();

        try {
            cartList = cartItemDAO.selectCartList(userId);
            cartItemDAO.deleteMenuList(userId);

            long totalPrice = orderTransactionService
                .order(orderDTO, cartList, orderMenuList, orderMenuOptionList);
            orderTransactionService.pay(payType, totalPrice, orderDTO.getId());
            orderMapper.completeOrder(totalPrice, orderDTO.getId(), OrderStatus.COMPLETE_ORDER);

        } catch (RuntimeException runtimeException) {
            cartItemDAO.insertMenuList(userId, cartList);
            throw runtimeException; // catch로 잡아서 throw 하지 않으면 롤백이 되지 않음.
        }
    }

    private OrderDTO makeOrderDTO(UserInfoDTO userInfo, long storeId) {
        OrderDTO orderDTO = OrderDTO.builder()
            .address(userInfo.getAddress())
            .userId(userInfo.getId())
            .orderStatus(OrderStatus.BEFORE_ORDER)
            .storeId(storeId)
            .build();
        return orderDTO;
    }

}
