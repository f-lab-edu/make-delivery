package com.flab.makedel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RiderDTO {

    @NotNull
    private final String id;

    @NotNull
    private final String name;

    @NotNull
    private final String phone;

    @NotNull
    private final String address;

    @NotNull
    private final String updatedAt;

    @JsonCreator
    public RiderDTO(@JsonProperty(value = "id") String id,
        @JsonProperty(value = "name") String name,
        @JsonProperty(value = "phone") String phone,
        @JsonProperty(value = "address") String address,
        @JsonProperty(value = "updatedAt") String updatedAt) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.updatedAt = updatedAt;
    }

}
