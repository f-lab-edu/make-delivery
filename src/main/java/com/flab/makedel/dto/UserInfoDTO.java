package com.flab.makedel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UserInfoDTO {

    private final String id;

    private final String name;

    private final String phone;

    private final String address;

    @JsonCreator
    public UserInfoDTO(@JsonProperty(value = "id") String id,
        @JsonProperty(value = "name") String name,
        @JsonProperty(value = "phone") String phone,
        @JsonProperty(value = "address") String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

}
