package com.flab.makedel.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderStoreDetailDTO {

    private final Long orderId;

    private final LocalDateTime orderCreatedAt;

    private final String orderStatus;

    private final Long totalPrice;

    private UserInfoDTO userInfo;

    private List<OrderDetailMenuDTO> menuList;

}
