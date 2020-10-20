package com.flab.makedel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreInfoDTO {

    private final Long storeId;

    private final String name;

    private final String phone;

    private final String address;

}