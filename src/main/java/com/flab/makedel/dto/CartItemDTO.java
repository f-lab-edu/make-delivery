package com.flab.makedel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class CartItemDTO {

    private String name;

    private Long price;

    private Long menuId;

    private Long storeId;

    private Long count;

    private List<CartOptionDTO> optionList;

    @JsonCreator
    public CartItemDTO(@JsonProperty(value = "name") String name,
        @JsonProperty(value = "price") Long price,
        @JsonProperty(value = "menuId") Long menuId,
        @JsonProperty(value = "storeId") Long storeId,
        @JsonProperty(value = "count") Long count,
        @JsonProperty(value = "optionList") List<CartOptionDTO> optionList
    ) {
        this.name = name;
        this.price = price;
        this.menuId = menuId;
        this.storeId = storeId;
        this.count = count;
        this.optionList = optionList;
    }


}
