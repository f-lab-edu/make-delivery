package com.flab.makedel.service;

import com.flab.makedel.Exception.NotEnoughBalanceException;
import com.flab.makedel.Exception.NotExistIdException;
import com.flab.makedel.dto.PayDTO;
import com.flab.makedel.dto.PayDTO.PayStatus;
import com.flab.makedel.dto.PayDTO.PayType;
import com.flab.makedel.mapper.OrderMapper;
import com.flab.makedel.mapper.PayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NaverPayService implements PayService {

    private final PayMapper payMapper;

    @Override
    public void pay(long price, long orderId) {

        PayDTO payDTO = PayDTO.builder()
            .payType(PayType.NAVER_PAY)
            .price(price)
            .orderId(orderId)
            .status(PayStatus.COMPLETE_PAY)
            .build();

        try {
            payMapper.insertPay(payDTO);
        } catch (NotEnoughBalanceException e) {
            throw new NotEnoughBalanceException("네이버 페이의 잔액이 부족합니다");
        } catch (RuntimeException e) {
            throw new NotExistIdException("존재하지 않는 주문 아이디입니다");
        }
    }
}
