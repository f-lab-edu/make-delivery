package com.flab.makedel.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.flab.makedel.dto.MenuDTO;
import com.flab.makedel.mapper.MenuMapper;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

    @Mock
    MenuMapper menuMapper;

    @InjectMocks
    MenuService menuService;

    MenuDTO menuDTO;

    @BeforeEach
    public void init() {
        menuDTO = MenuDTO.builder()
            .name("뿌링클")
            .price(12300L)
            .photo("34.jpg")
            .description("맛있습니다")
            .menuGroupId(2L)
            .storeId(3L)
            .build();
    }

    @Test
    @DisplayName("사장님이 가게 메뉴를 추가한다")
    public void insertMenuTest() {
        doNothing().when(menuMapper).insertMenu(any(MenuDTO.class));

        menuService.insertMenu(menuDTO);

        verify(menuMapper).insertMenu(any(MenuDTO.class));
    }

    @Test
    @DisplayName("사장님이 가게 메뉴를 삭제한다")
    public void deleteMenuTest() {
        doNothing().when(menuMapper).deleteMenu(anyLong());

        menuService.deleteMenu(12L);

        verify(menuMapper).deleteMenu(anyLong());
    }

    @Test
    @DisplayName("모든 사용자가 가게 메뉴를 조회한다")
    public void loadStoreMenuTest() {
        when(menuMapper.selectStoreMenu(anyLong())).thenReturn(anyList());

        menuService.loadStoreMenu(12L);

        verify(menuMapper).selectStoreMenu(anyLong());
    }

    @Test
    @DisplayName("가게 메뉴를 조회할 때 없는 가게 아이디로 조회를 하면 빈 리스트를 리턴한다")
    public void loadStoreMenuTestReturnEmptyList() {
        when(menuMapper.selectStoreMenu(anyLong())).thenReturn(new ArrayList<MenuDTO>());

        assertEquals(menuService.loadStoreMenu(12L).isEmpty(), true);

        verify(menuMapper).selectStoreMenu(anyLong());
    }
}
