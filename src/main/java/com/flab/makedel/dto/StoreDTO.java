package com.flab.makedel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreDTO {

    private final Long id;

    private final String name;

    private final String phone;

    private final String address;

    private final String ownerId;

    private final String openStatus;

    private final String introduction;

    private final Long categoryId;

    @JsonCreator
    public StoreDTO(@JsonProperty(value = "id") Long id,
        @JsonProperty(value = "name") String name,
        @JsonProperty(value = "phone") String phone,
        @JsonProperty(value = "address") String address,
        @JsonProperty(value = "ownerId") String ownerId,
        @JsonProperty(value = "openStatus") String openStatus,
        @JsonProperty(value = "introduction") String introduction,
        @JsonProperty(value = "categoryId") Long categoryId
    ) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.ownerId = ownerId;
        this.openStatus = openStatus;
        this.introduction = introduction;
        this.categoryId = categoryId;
    }

}
