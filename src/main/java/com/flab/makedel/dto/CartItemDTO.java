package com.flab.makedel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CartItemDTO {

    @NotNull
    private final String name;

    @NotNull
    private final Long price;

    @NotNull
    private final Long menuId;

    @NotNull
    private final Long storeId;

    @NotNull
    private final Long count;

    @NotNull
    private final List<CartOptionDTO> optionList;

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
