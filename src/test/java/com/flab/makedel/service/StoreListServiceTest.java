package com.flab.makedel.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.flab.makedel.dto.StoreCategoryDTO;
import com.flab.makedel.dto.StoreDTO;
import com.flab.makedel.mapper.StoreListMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StoreListServiceTest {

    @Mock
    StoreListMapper storeListMapper;

    @InjectMocks
    StoreListService storeListService;

    @Test
    @DisplayName("올바른 카테고리 아이디로 음식점 목록을 조회한다")
    public void getStoreListByCategoryTest() {
        when(storeListMapper.selectStoreListByCategory(anyLong())).thenReturn(anyList());

        storeListService.getStoreListByCategory(1L);

        verify(storeListMapper).selectStoreListByCategory(anyLong());
    }

    @Test
    @DisplayName("올바른 카테고리 아이디로 음식점 목록을 조회했는데 음식점 목록이 없다면 빈 리스트를 리턴한다")
    public void getStoreListByCategoryTestReturnEmptyList() {
        when(storeListMapper.selectStoreListByCategory(anyLong())).thenReturn(new ArrayList<>());

        assertEquals(storeListService.getStoreListByCategory(2L).isEmpty(), true);

        verify(storeListMapper).selectStoreListByCategory(anyLong());
    }

    @Test
    @DisplayName("잘못된 카테고리 아이디로 음식점 목록을 조회하면 RuntimeException을 던진다")
    public void getStoreListByCategoryTestFailBecauseWrongId() {
        doThrow(RuntimeException.class).when(storeListMapper).selectStoreListByCategory(anyLong());

        assertThrows(RuntimeException.class, () -> storeListService.getStoreListByCategory(100L));

        verify(storeListMapper).selectStoreListByCategory(anyLong());
    }

    @Test
    @DisplayName("음식 카테고리 전체 목록을 조회한다")
    public void getAllStoreCategoryTest() {
        when(storeListMapper.selectCategoryList()).thenReturn(new ArrayList<StoreCategoryDTO>());

        storeListService.getAllStoreCategory();

        verify(storeListMapper).selectCategoryList();
    }

}