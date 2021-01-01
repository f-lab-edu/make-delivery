package com.flab.makedel.service;

import com.flab.makedel.Exception.MaxPayLimitException;
import com.flab.makedel.Exception.NotExistIdException;
import com.flab.makedel.dto.PayDTO;
import com.flab.makedel.dto.PayDTO.PayStatus;
import com.flab.makedel.dto.PayDTO.PayType;
import com.flab.makedel.mapper.OrderMapper;
import com.flab.makedel.mapper.PayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardPayService implements PayService {

    private final PayMapper payMapper;
    private static final long maxCardPayLimit = 2000000L;

    @Override
    public void pay(long price, long orderId) {

        PayDTO payDTO = PayDTO.builder()
            .payType(PayType.CARD)
            .price(price)
            .orderId(orderId)
            .status(PayStatus.COMPLETE_PAY)
            .build();

        if (price > maxCardPayLimit) {
            throw new MaxPayLimitException("카드 결제에 상한 금액을 초과했습니다");
        }

        try {
            payMapper.insertPay(payDTO);
        } catch (RuntimeException e) {
            throw new NotExistIdException("존재하지 않는 주문 아이디입니다");
        }
    }
}


