package com.flab.makedel.mapper;

import com.flab.makedel.dto.OrderDTO;
import com.flab.makedel.dto.OrderDTO.OrderStatus;

public interface OrderMapper {

    void insertOrder(OrderDTO orderDTO);

    void completeOrder(long totalPrice, long orderId, OrderStatus orderStatus);

}
