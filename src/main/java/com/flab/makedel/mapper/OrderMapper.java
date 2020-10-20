package com.flab.makedel.mapper;

import com.flab.makedel.dto.OrderDTO;
import com.flab.makedel.dto.OrderDTO.OrderStatus;
import com.flab.makedel.dto.OrderDetailDTO;
import com.flab.makedel.dto.OrderStoreDetailDTO;
import java.util.List;

public interface OrderMapper {

    void insertOrder(OrderDTO orderDTO);

    void completeOrder(long totalPrice, long orderId, OrderStatus orderStatus);

    OrderDetailDTO selectDetailOrder(long orderId);

    List<OrderStoreDetailDTO> selectDetailStoreOrder(long storeId);

}
