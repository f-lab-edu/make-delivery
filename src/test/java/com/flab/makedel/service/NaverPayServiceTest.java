package com.flab.makedel.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.flab.makedel.Exception.NotEnoughBalanceException;
import com.flab.makedel.Exception.NotExistIdException;
import com.flab.makedel.dto.OptionDTO;
import com.flab.makedel.dto.PayDTO;
import com.flab.makedel.dto.PayDTO.PayStatus;
import com.flab.makedel.dto.PayDTO.PayType;
import com.flab.makedel.mapper.OrderMapper;
import com.flab.makedel.mapper.PayMapper;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.omg.SendingContext.RunTime;

@ExtendWith(MockitoExtension.class)
public class NaverPayServiceTest {

    @Mock
    PayMapper payMapper;

    @InjectMocks
    NaverPayService naverPayService;

    PayDTO payDTO;

    @BeforeEach
    public void init() {
        payDTO = PayDTO.builder()
            .payType(PayType.NAVER_PAY)
            .price(10000L)
            .orderId(1L)
            .status(PayStatus.BEFORE_PAY)
            .build();
    }

    @Test
    @DisplayName("주문 시 네이버페이로 결제하는데 성공한다")
    public void payTest() {
        doNothing().when(payMapper).insertPay(any(PayDTO.class));

        naverPayService.pay(payDTO.getPrice(), payDTO.getOrderId());

        verify(payMapper).insertPay(any(PayDTO.class));
    }

    @Test
    @DisplayName("존재하지 않는 주문 아이디로 결제 시도를 하면 NotExistIdException을 던진다")
    public void payTestFailBecauseNotExistIdException() {
        doThrow(RuntimeException.class).when(payMapper).insertPay(any(PayDTO.class));

        assertThrows(NotExistIdException.class, () -> naverPayService.pay(12300L, 12L));

        verify(payMapper).insertPay(any(PayDTO.class));
    }

    @Test
    @DisplayName("네이버 페이의 잔액이 부족하여 결제 시도를 하면 NotEnoughBalanceException을 던진다")
    public void payTestFailBecauseNotEnoughBalanceException() {
        doThrow(NotEnoughBalanceException.class).when(payMapper).insertPay(any(PayDTO.class));

        assertThrows(NotEnoughBalanceException.class, () -> naverPayService.pay(12300L, 1L));

        verify(payMapper).insertPay(any(PayDTO.class));
    }


}
