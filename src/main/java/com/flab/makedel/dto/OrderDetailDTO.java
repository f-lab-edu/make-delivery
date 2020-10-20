package com.flab.makedel.dto;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderDetailDTO {

    private final Long orderId;

    private final String orderStatus;

    private final String userId;

    private final Long totalPrice;

    private StoreInfoDTO storeInfo;

    private List<OrderDetailMenuDTO> menuList;


}
