package com.flab.makedel.service;

import com.flab.makedel.dto.PayDTO;
import com.flab.makedel.dto.PayDTO.PayStatus;
import com.flab.makedel.dto.PayDTO.PayType;
import com.flab.makedel.mapper.PayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepositPayService implements PayService {

    private final PayMapper payMapper;

    @Override
    public void pay(long price, long orderId) {
        PayDTO payDTO = PayDTO.builder()
            .payType(PayType.DEPOSIT)
            .price(price)
            .orderId(orderId)
            .status(PayStatus.무통장입금대기)
            .build();
        payMapper.insertPay(payDTO);
    }
}
