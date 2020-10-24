package com.flab.makedel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class CartItemDTO {

    @NotNull
    private String name;

    @NotNull
    private Long price;

    @NotNull
    private Long menuId;

    @NotNull
    private Long storeId;

    @NotNull
    private Long count;

    @NotNull
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

    public CartItemDTO(Long menuId, String name, Long price, Long count) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.count = count;
    }


}
