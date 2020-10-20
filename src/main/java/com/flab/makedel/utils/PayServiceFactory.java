package com.flab.makedel.utils;

import com.flab.makedel.dto.PayDTO.PayType;
import com.flab.makedel.service.CardPayService;
import com.flab.makedel.service.DepositPayService;
import com.flab.makedel.service.NaverPayService;
import com.flab.makedel.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayServiceFactory {

    private final CardPayService cardPayService;
    private final NaverPayService naverPayService;
    private final DepositPayService depositPayService;

    public PayService getPayService(PayType payType) {

        PayService payService = null;

        switch (payType) {
            case CARD:
                payService = cardPayService;
                break;
            case NAVER_PAY:
                payService = naverPayService;
                break;
            case DEPOSIT:
                payService = depositPayService;
                break;
            default:
                throw new IllegalArgumentException();
        }

        return payService;
    }

}
