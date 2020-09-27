package com.flab.makedel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CartOptionDTO {

    private Long optionId;

    private String name;

    private Long price;

    @JsonCreator
    public CartOptionDTO(
        @JsonProperty(value = "optionId") Long optionId,
        @JsonProperty(value = "name") String name,
        @JsonProperty(value = "price") Long price) {
        this.optionId = optionId;
        this.name = name;
        this.price = price;
    }

}
