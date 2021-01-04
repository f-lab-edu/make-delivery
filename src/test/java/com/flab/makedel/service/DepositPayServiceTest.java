package com.flab.makedel.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

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

@ExtendWith(MockitoExtension.class)
public class DepositPayServiceTest {

    @Mock
    PayMapper payMapper;

    @Mock
    OrderMapper orderMapper;

    @InjectMocks
    DepositPayService depositPayService;

    PayDTO payDTO;

    @BeforeEach
    public void init() {
        payDTO = PayDTO.builder()
            .payType(PayType.CARD)
            .price(10000L)
            .orderId(1L)
            .status(PayStatus.BEFORE_PAY)
            .build();
    }

    @Test
    @DisplayName("주문 시 무통장입금으로 결제하는데 성공한다")
    public void payTest() {
        when(orderMapper.isExistsId(anyLong())).thenReturn(true);
        doNothing().when(payMapper).insertPay(any(PayDTO.class));

        depositPayService.pay(payDTO.getPrice(), payDTO.getOrderId());

        verify(orderMapper).isExistsId(anyLong());
        verify(payMapper).insertPay(any(PayDTO.class));
    }

    @Test
    @DisplayName("존재하지 않는 주문 아이디로 결제 시도를 하면 NotExistIdException 던진다")
    public void payTestFailBecauseNotExistIdException() {
        when(orderMapper.isExistsId(anyLong())).thenReturn(false);

        assertThrows(NotExistIdException.class, () -> depositPayService.pay(12300L, 12L));

        verify(orderMapper).isExistsId(anyLong());
    }


}
