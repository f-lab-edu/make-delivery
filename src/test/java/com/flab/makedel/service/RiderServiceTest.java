package com.flab.makedel.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.flab.makedel.dao.DeliveryDAO;
import com.flab.makedel.dto.OrderDTO.OrderStatus;
import com.flab.makedel.dto.RiderDTO;
import com.flab.makedel.mapper.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RiderServiceTest {

    @InjectMocks
    RiderService riderService;

    @Mock
    DeliveryDAO deliveryDAO;

    @Mock
    OrderMapper orderMapper;
    
    RiderDTO riderDTO;

    @BeforeEach
    public void init() {
        riderDTO = RiderDTO.builder()
            .id("일산라이더")
            .name("이성국")
            .phone("010-1111-1111")
            .address("경기도 고양시 일산동")
            .fcmToken("sdkfjkclxvwer1234")
            .build();
    }

    @Test
    @DisplayName("라이더가 출근할 시 지역별 라이더 대기 목록에 등록된다")
    public void registerStandbyRiderWhenStartWorkTest() {
        doNothing().when(deliveryDAO).insertStandbyRiderWhenStartWork(any(RiderDTO.class));

        deliveryDAO.insertStandbyRiderWhenStartWork(riderDTO);

        verify(deliveryDAO).insertStandbyRiderWhenStartWork(any(RiderDTO.class));
    }

    @Test
    @DisplayName("라이더가 출근할 시 지역별 라이더 대기 목록에 등록하는데 아이디 값을 누락하고 요청하여 실패한다")
    public void registerStandbyRiderWhenStartWorkTestFailBecauseEmptyId() {
        riderDTO = RiderDTO.builder()
            .name("이성국")
            .build();
        doThrow(IllegalArgumentException.class).when(deliveryDAO)
            .insertStandbyRiderWhenStartWork(any(RiderDTO.class));

        assertThrows(IllegalArgumentException.class,
            () -> deliveryDAO.insertStandbyRiderWhenStartWork(riderDTO));

        verify(deliveryDAO).insertStandbyRiderWhenStartWork(any(RiderDTO.class));
    }

    @Test
    @DisplayName("라이더가 출근할 시 지역별 라이더 대기 목록에 등록하는데 토큰값을 누락하고 요청하면 실패한다")
    public void registerStandbyRiderWhenStartWorkTestFailBecauseEmptyToken() {
        riderDTO = RiderDTO.builder()
            .id("일산라이더")
            .build();
        doThrow(IllegalArgumentException.class).when(deliveryDAO)
            .insertStandbyRiderWhenStartWork(any(RiderDTO.class));

        assertThrows(IllegalArgumentException.class,
            () -> deliveryDAO.insertStandbyRiderWhenStartWork(riderDTO));

        verify(deliveryDAO).insertStandbyRiderWhenStartWork(any(RiderDTO.class));
    }

    @Test
    @DisplayName("라이더가 출근할 시 지역별 라이더 대기 목록에 등록하는데 주소값을 누락하고 요청하면 실패한다")
    public void registerStandbyRiderWhenStartWorkTestFailBecauseEmptyAddress() {
        riderDTO = RiderDTO.builder()
            .id("일산라이더")
            .build();
        doThrow(IllegalArgumentException.class).when(deliveryDAO)
            .insertStandbyRiderWhenStartWork(any(RiderDTO.class));

        assertThrows(IllegalArgumentException.class,
            () -> deliveryDAO.insertStandbyRiderWhenStartWork(riderDTO));

        verify(deliveryDAO).insertStandbyRiderWhenStartWork(any(RiderDTO.class));
    }

    @Test
    @DisplayName("라이더가 퇴근할 시 지역별 라이더 대기 목록에서 삭제한다")
    public void deleteStandbyRiderWhenStopWorkTest() {
        doNothing().when(deliveryDAO).deleteStandbyRiderWhenStopWork(any(RiderDTO.class));

        deliveryDAO.deleteStandbyRiderWhenStopWork(riderDTO);

        verify(deliveryDAO).deleteStandbyRiderWhenStopWork(any(RiderDTO.class));
    }

    @Test
    @DisplayName("라이더가 퇴근할 시 지역별 라이더 대기 목록에서 삭제할 때 아이디를 누락하여 보내면 실패한다")
    public void deleteStandbyRiderWhenStopWorkTestFailBecauseEmptyId() {
        riderDTO = RiderDTO.builder()
            .name("이성국")
            .phone("010-1111-1111")
            .address("경기도 고양시 일산동")
            .fcmToken("sdkfjkclxvwer1234")
            .build();
        doThrow(IllegalArgumentException.class).when(deliveryDAO)
            .deleteStandbyRiderWhenStopWork(any(RiderDTO.class));

        assertThrows(IllegalArgumentException.class,
            () -> deliveryDAO.deleteStandbyRiderWhenStopWork(riderDTO));

        verify(deliveryDAO).deleteStandbyRiderWhenStopWork(any(RiderDTO.class));
    }

    @Test
    @DisplayName("라이더가 퇴근할 시 지역별 라이더 대기 목록에서 삭제할 때 주소를 누락하여 보내면 실패한다")
    public void deleteStandbyRiderWhenStopWorkTestFailBecauseEmptyAddress() {
        riderDTO = RiderDTO.builder()
            .id("rider")
            .name("이성국")
            .phone("010-1111-1111")
            .fcmToken("sdkfjkclxvwer1234")
            .build();
        doThrow(IllegalArgumentException.class).when(deliveryDAO)
            .deleteStandbyRiderWhenStopWork(any(RiderDTO.class));

        assertThrows(IllegalArgumentException.class,
            () -> deliveryDAO.deleteStandbyRiderWhenStopWork(riderDTO));

        verify(deliveryDAO).deleteStandbyRiderWhenStopWork(any(RiderDTO.class));
    }

    @Test
    @DisplayName("라이더가 주문완료된 음식에 배차 요청을 하는데 성공한다")
    public void acceptStandbyOrderTest() {
        doNothing().when(deliveryDAO)
            .updateStandbyOrderToDelivering(anyLong(), any(RiderDTO.class));
        doNothing().when(orderMapper)
            .updateStandbyOrderToDelivering(anyLong(), any(String.class), any(
                OrderStatus.class));

        riderService.acceptStandbyOrder(1L, riderDTO);

        verify(deliveryDAO).updateStandbyOrderToDelivering(anyLong(), any(RiderDTO.class));
        verify(orderMapper).updateStandbyOrderToDelivering(anyLong(), any(String.class), any(
            OrderStatus.class));
    }

    @Test
    @DisplayName("라이더가 잘못된 주문 아이디로 배차 요청을 하면 IllegalArgumentException을 던진다")
    public void acceptStandbyOrderTestFailBecauseWrongOrderId() {
        doThrow(IllegalArgumentException.class).when(deliveryDAO)
            .updateStandbyOrderToDelivering(anyLong(), any(RiderDTO.class));

        assertThrows(IllegalArgumentException.class,
            () -> riderService.acceptStandbyOrder(100L, riderDTO));

        verify(deliveryDAO).updateStandbyOrderToDelivering(anyLong(), any(RiderDTO.class));
    }

    @Test
    @DisplayName("라이더가 잘못된 라이더 아이디로 배차 요청을 하면 IllegalArgumentException을 던진다")
    public void acceptStandbyOrderTestFailBecauseWrongRiderId() {
        doThrow(IllegalArgumentException.class).when(deliveryDAO)
            .updateStandbyOrderToDelivering(anyLong(), any(RiderDTO.class));

        assertThrows(IllegalArgumentException.class,
            () -> riderService.acceptStandbyOrder(100L, riderDTO));

        verify(deliveryDAO).updateStandbyOrderToDelivering(anyLong(), any(RiderDTO.class));
    }

    @Test
    @DisplayName("라이더가 잘못된 주소값으로 배차 요청을 하면 IllegalArgumentException을 던진다")
    public void acceptStandbyOrderTestFailBecauseWrongAddress() {
        doThrow(IllegalArgumentException.class).when(deliveryDAO)
            .updateStandbyOrderToDelivering(anyLong(), any(RiderDTO.class));

        assertThrows(IllegalArgumentException.class,
            () -> riderService.acceptStandbyOrder(100L, riderDTO));

        verify(deliveryDAO).updateStandbyOrderToDelivering(anyLong(), any(RiderDTO.class));
    }


}