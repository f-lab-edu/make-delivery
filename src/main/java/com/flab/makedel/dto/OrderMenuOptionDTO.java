package com.flab.makedel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OrderMenuOptionDTO {

    private final Long id;

    private final Long optionId;

    private final Long menuId;

    private final Long orderId;

}
