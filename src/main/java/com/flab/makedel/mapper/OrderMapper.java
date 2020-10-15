package com.flab.makedel.mapper;

import com.flab.makedel.dto.OrderDTO;
import com.flab.makedel.dto.OrderDTO.OrderStatus;
import com.flab.makedel.dto.OrderDetailDTO;

public interface OrderMapper {

    void insertOrder(OrderDTO orderDTO);

    void completeOrder(long totalPrice, long orderId, OrderStatus orderStatus);

    OrderDetailDTO selectDetailOrder(long orderId);

}
