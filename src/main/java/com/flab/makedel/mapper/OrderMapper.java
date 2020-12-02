package com.flab.makedel.mapper;

import com.flab.makedel.annotation.SetDataSource;
import com.flab.makedel.annotation.SetDataSource.DataSourceType;
import com.flab.makedel.dto.OrderDTO;
import com.flab.makedel.dto.OrderDTO.OrderStatus;
import com.flab.makedel.dto.OrderDetailDTO;
import com.flab.makedel.dto.OrderReceiptDTO;
import com.flab.makedel.dto.OrderStoreDetailDTO;
import java.util.List;

public interface OrderMapper {

    @SetDataSource(dataSourceType = DataSourceType.MASTER)
    void insertOrder(OrderDTO orderDTO);

    @SetDataSource(dataSourceType = DataSourceType.MASTER)
    void completeOrder(long totalPrice, long orderId, OrderStatus orderStatus);

    @SetDataSource(dataSourceType = DataSourceType.SLAVE)
    OrderReceiptDTO selectOrderReceipt(long orderId);

    @SetDataSource(dataSourceType = DataSourceType.SLAVE)
    List<OrderStoreDetailDTO> selectDetailStoreOrder(long storeId);

    @SetDataSource(dataSourceType = DataSourceType.MASTER)
    void approveOrder(long orderId, OrderStatus orderStatus);

    @SetDataSource(dataSourceType = DataSourceType.MASTER)
    void updateStandbyOrderToDelivering(long orderId, String riderId, OrderStatus orderStatus);

    @SetDataSource(dataSourceType = DataSourceType.MASTER)
    void finishDeliveringOrder(long orderId, OrderStatus orderStatus);

}
