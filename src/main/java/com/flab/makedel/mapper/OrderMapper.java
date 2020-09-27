package com.flab.makedel.mapper;

import com.flab.makedel.dto.OrderDTO;

public interface OrderMapper {

    void insertOrder(OrderDTO orderDTO);

    void updateTotalPrice(long totalPrice, long orderId);

}
