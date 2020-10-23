package com.flab.makedel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flab.makedel.dto.OrderDTO.OrderStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderReceiptDTO {

    private final Long orderId;

    private final OrderStatus orderStatus;

    private final UserInfoDTO userInfo;

    private final Long totalPrice;

    private final StoreInfoDTO storeInfo;

    private final List<CartItemDTO> cartList;

    @JsonCreator
    public OrderReceiptDTO(@JsonProperty(value = "orderId") Long orderId,
        @JsonProperty(value = "orderStatus") OrderStatus orderStatus,
        @JsonProperty(value = "userInfo") UserInfoDTO userInfo,
        @JsonProperty(value = "totalPrice") Long totalPrice,
        @JsonProperty(value = "storeInfo") StoreInfoDTO storeInfo,
        @JsonProperty(value = "cartList") List<CartItemDTO> cartList) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.userInfo = userInfo;
        this.totalPrice = totalPrice;
        this.storeInfo = storeInfo;
        this.cartList = cartList;
    }

}
