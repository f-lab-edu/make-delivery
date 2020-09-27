package com.flab.makedel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OrderMenuOptionDTO {

    private final Long menuId;

    private final Long orderId;

    private final Long optionId;

}
