package com.flab.makedel.dto;

import com.flab.makedel.dto.OrderDTO.OrderStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OrderReceiptDTO {

    private final Long orderId;

    private final OrderStatus orderStatus;

    private final UserInfoDTO userInfo;

    private final Long totalPrice;

    private final StoreInfoDTO storeInfo;

    private final List<CartItemDTO> cartList;

}
