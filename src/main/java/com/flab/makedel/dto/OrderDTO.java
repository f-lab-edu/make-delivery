package com.flab.makedel.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OrderDTO {

    private final Long id;

    private final LocalDateTime createdAt;

    private final String orderStatus;

    @NotBlank
    private final String address;

    @NotBlank
    private final String userId;

    @NotBlank
    private final Long storeId;

    private final Long totalPrice;

}
