package com.flab.makedel.service;

import com.flab.makedel.dto.PayDTO;
import com.flab.makedel.mapper.PayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardPayService implements PayService {

    private static final String PAY_TYPE = "CARD";
    private static final String COMPLETE_PAY = "결제완료";

    private final PayMapper payMapper;

    @Override
    public void registerPay(long price, long orderId) {

        PayDTO payDTO = PayDTO.builder()
            .payType(PAY_TYPE)
            .price(price)
            .orderId(orderId)
            .status(COMPLETE_PAY)
            .build();

        payMapper.insertPay(payDTO);

    }
}

