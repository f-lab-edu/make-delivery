package com.flab.makedel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDTO {

    private final String id;

    private final String name;

    private final String phone;

    private final String address;

}
