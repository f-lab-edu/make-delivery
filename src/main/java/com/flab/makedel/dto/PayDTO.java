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
    private final PayType payType;

    @NotNull
    private final Long price;

    @NotNull
    private final LocalDateTime createdAt;

    @NotNull
    private final Long orderId;

    @NotNull
    private final PayStatus status;

    public enum PayType {
        CARD, NAVER_PAY, DEPOSIT
    }

    public enum PayStatus {
        결제전, 무통장입금대기, 결제완료
    }

}
