package com.flab.makedel.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PayDTO {

    private final Long id;

    @NotNull
    private final String payType;

    @NotNull
    private final Long price;

    @NotNull
    private final LocalDateTime createdAt;

    @NotNull
    private final Long orderId;

    @NotNull
    private final String status;

}
