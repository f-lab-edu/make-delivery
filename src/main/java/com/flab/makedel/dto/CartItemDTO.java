package com.flab.makedel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class CartItemDTO {

    private String name;

    private String price;

    private String storeId;

    private List<CartOptionDTO> optionList;

    @JsonCreator
    public CartItemDTO(@JsonProperty(value = "name") String name,
        @JsonProperty(value = "price") String price,
        @JsonProperty(value = "storeId") String storeId,
        @JsonProperty(value = "optionList") List<CartOptionDTO>optionList
    ) {
        this.name = name;
        this.price = price;
        this.storeId = storeId;
        this.optionList = optionList;
    }


}
