package com.flab.makedel.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MenuDTO {

    private final Long id;

    private final String name;

    private final Long price;

    private final String photo;

    private final String description;

    private final Long menuGroupId;

    private final Long storeId;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

}
