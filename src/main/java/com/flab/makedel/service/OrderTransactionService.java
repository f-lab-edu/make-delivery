package com.flab.makedel.service;

import com.flab.makedel.dto.CartItemDTO;
import com.flab.makedel.dto.CartOptionDTO;
import com.flab.makedel.dto.OrderDTO;
import com.flab.makedel.dto.OrderMenuDTO;
import com.flab.makedel.dto.OrderMenuOptionDTO;
import com.flab.makedel.dto.PayDTO.PayType;
import com.flab.makedel.mapper.OrderMapper;
import com.flab.makedel.mapper.OrderMenuMapper;
import com.flab.makedel.mapper.OrderMenuOptionMapper;
import com.flab.makedel.utils.PayServiceFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
public class OrderTransactionService {

    private final OrderMapper orderMapper;
    private final OrderMenuMapper orderMenuMapper;
    private final OrderMenuOptionMapper orderMenuOptionMapper;
    private final PayServiceFactory payServiceFactory;

    @Transactional
    public long order(OrderDTO orderDTO, List<CartItemDTO> cartList) {

        orderMapper.insertOrder(orderDTO);
        long totalPrice = registerOrderMenu(cartList, orderDTO.getId());

        return totalPrice;
    }

    @Transactional
    public void pay(PayType payType, long totalPrice, long orderId) {

        PayService payService = payServiceFactory.getPayService(payType);
        payService.pay(totalPrice, orderId);

    }

    public long registerOrderMenu(List<CartItemDTO> cartList, Long orderId) {

        List<OrderMenuDTO> orderMenuList = new ArrayList<>();
        List<OrderMenuOptionDTO> orderMenuOptionList = new ArrayList<>();
        long totalPrice = 0;

        for (int i = 0; i < cartList.size(); i++) {
            CartItemDTO cart = cartList.get(i);
            totalPrice += cart.getPrice() * cart.getCount();

            OrderMenuDTO orderMenuDTO = OrderMenuDTO.builder()
                .orderId(orderId)
                .menuId(cart.getMenuId())
                .count(cart.getCount())
                .build();
            orderMenuList.add(orderMenuDTO);

            for (int j = 0; j < cart.getOptionList().size(); j++) {
                CartOptionDTO option = cart.getOptionList().get(j);
                totalPrice += option.getPrice();

                OrderMenuOptionDTO orderMenuOptionDTO = OrderMenuOptionDTO.builder()
                    .optionId(option.getOptionId())
                    .menuId(cart.getMenuId())
                    .orderId(orderId)
                    .build();
                orderMenuOptionList.add(orderMenuOptionDTO);
            }
        }

        orderMenuMapper.insertOrderMenu(orderMenuList);
        orderMenuOptionMapper.insertOrderMenuOption(orderMenuOptionList);

        return totalPrice;

    }

}
