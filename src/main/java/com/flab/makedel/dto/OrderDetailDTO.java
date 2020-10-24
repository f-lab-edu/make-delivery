package com.flab.makedel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flab.makedel.dto.OrderDTO.OrderStatus;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderDetailDTO {

    private final Long orderId;

    private final String orderStatus;

    private final Long totalPrice;

    private UserInfoDTO userInfo;

    private StoreInfoDTO storeInfo;

    private List<OrderDetailMenuDTO> menuList;
    
}
