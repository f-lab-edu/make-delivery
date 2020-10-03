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

        if (payType == PayType.CARD) {
            payService = cardPayService;
        } else if (payType == PayType.NAVER_PAY) {
            payService = naverPayService;
        } else if (payType == PayType.DEPOSIT) {
            payService = depositPayService;
        } else {
            throw new IllegalArgumentException();
        }

        return payService;
    }

}
