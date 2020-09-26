package com.flab.makedel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CartOptionDTO {

    private String name;

    private Long price;

    @JsonCreator
    public CartOptionDTO(@JsonProperty(value = "name") String name,
        @JsonProperty(value = "price") Long price) {
        this.name = name;
        this.price = price;
    }

}
