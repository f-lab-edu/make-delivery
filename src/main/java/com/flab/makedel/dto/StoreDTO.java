package com.flab.makedel.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class StoreDTO {

    private final String id;

    private final String name;

    private final String phone;

    private final String address;

    private final String ownerId;

    private final String openStatus;

    private final String introduction;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

}
