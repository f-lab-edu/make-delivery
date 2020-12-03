package com.flab.makedel.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.*;

import com.flab.makedel.dto.OrderDTO.OrderStatus;
import com.flab.makedel.dto.OrderReceiptDTO;
import com.flab.makedel.dto.StoreDTO;
import com.flab.makedel.mapper.OrderMapper;
import com.flab.makedel.mapper.StoreMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @Mock
    StoreMapper storeMapper;

    @Mock
    OrderMapper orderMapper;

    @InjectMocks
    StoreService storeService;

    StoreDTO store;

    @BeforeEach
    public void makeStore() {
        store = StoreDTO.builder()
            .name("60계 치킨")
            .phone("031-921-0101")
            .address("경기도 고양시 일산동")
            .introduction("고추 치킨이 맛있습니다")
            .categoryId("1")
            .build();
    }

    @Test
    @DisplayName("가게 생성에 성공합니다")
    public void insertStoreTestSuccess() {
        doNothing().when(storeMapper).insertStore(any(StoreDTO.class));

        storeService.insertStore(store, "60owners");

        verify(storeMapper).insertStore(any(StoreDTO.class));
    }

    @Test
    @DisplayName("가게 생성에 실패합니다 : 중복된 가게 이름")
    public void insertStoreTestFailBecauseSameStoreName() {
        doThrow(RuntimeException.class).when(storeMapper).insertStore(any(StoreDTO.class));

        assertThrows(RuntimeException.class,
            () -> storeService.insertStore(store, "60owners"));

        verify(storeMapper).insertStore(any(StoreDTO.class));
    }

    @Test
    @DisplayName("사장이 내 가게 전체 목록을 조회하는데 성공합니다")
    public void getMyAllStoreTestSuccess() {
        when(storeMapper.selectStoreList(any(String.class)))
            .thenReturn(anyList());

        storeService.getMyAllStore("owner");

        verify(storeMapper).selectStoreList(any(String.class));
    }

    @Test
    @DisplayName("사장이 내 특정 가게 목록을 조회하는데 성공합니다")
    public void getMyStoreTestSuccess() {
        when(storeMapper.selectStore(anyLong(), any(String.class)))
            .thenReturn(store);

        storeService.getMyStore(43, "owner");

        verify(storeMapper).selectStore(anyLong(), any(String.class));
    }

    @Test
    @DisplayName("자신의 가게가 맞는지 확인하는데 성공합니다.")
    public void validateMyStoreTestSuccess() {
        when(storeMapper.isMyStore(anyLong(), any(String.class))).thenReturn(true);

        storeService.validateMyStore(3, "owner");

        verify(storeMapper).isMyStore(anyLong(), any(String.class));
    }

    @Test
    @DisplayName("자신의 가게가 맞는지 확인하는데 실패합니다 : 본인 소유의 가게가 아님")
    public void validateMyStoreTestFail() {
        when(storeMapper.isMyStore(anyLong(), any(String.class))).thenReturn(false);

        assertThrows(HttpClientErrorException.class,
            () -> storeService.validateMyStore(3, "owner1"));

        verify(storeMapper).isMyStore(anyLong(), any(String.class));
    }

    @Test
    @DisplayName("사장이 주문을 승인하는데 성공합니다")
    public void approveOrderTestSuccess() {
        doNothing().when(orderMapper).approveOrder(anyLong(), any(OrderStatus.class));
        when(orderMapper.selectOrderReceipt(anyLong())).thenReturn(any(OrderReceiptDTO.class));
    }

}
