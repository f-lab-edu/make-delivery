package com.flab.makedel.dto;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderDetailMenuDTO {

    private final Long menuId;

    private final String menuName;

    private final Long menuPrice;

    private final Long menuCount;

    private List<OrderDetailOptionDTO> optionList;

}