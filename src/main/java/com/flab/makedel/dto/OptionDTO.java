package com.flab.makedel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OptionDTO {

    private final Long id;

    private final String name;

    private final Long price;

    private final Long menuId;

}
