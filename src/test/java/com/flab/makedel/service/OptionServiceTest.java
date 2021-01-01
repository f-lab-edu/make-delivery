package com.flab.makedel.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.flab.makedel.dto.OptionDTO;
import com.flab.makedel.mapper.OptionMapper;
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
public class OptionServiceTest {

    @Mock
    OptionMapper optionMapper;

    @InjectMocks
    OptionService optionService;

    List<OptionDTO> optionList;

    @BeforeEach
    public void init() {
        optionList = new ArrayList<OptionDTO>();
    }

    @Test
    @DisplayName("사장님이 음식 옵션을 추가한다")
    public void registerOptionListTest() {
        doNothing().when(optionMapper).insertOptionList(anyList());

        optionService.registerOptionList(optionList);

        verify(optionMapper).insertOptionList(anyList());
    }

    @Test
    @DisplayName("음식 메뉴에 대한 옵션리스트를 조회한다")
    public void loadOptionListTest() {
        when(optionMapper.selectOptionList(anyLong())).thenReturn(optionList);

        optionService.loadOptionList(12L);

        verify(optionMapper).selectOptionList(anyLong());
    }

    @Test
    @DisplayName("잘못된 메뉴 아이디로 음식 메뉴에 대한 옵션리스트를 조회하면 빈 리스트를 리턴한다")
    public void loadOptionListTestReturnEmptyListBecauseWrongMenuId() {
        when(optionMapper.selectOptionList(anyLong())).thenReturn(new ArrayList<>());

        assertEquals(optionService.loadOptionList(12L).isEmpty(), true);

        verify(optionMapper).selectOptionList(anyLong());
    }

    @Test
    @DisplayName("잘못된 가게 아이디로 음식 메뉴에 대한 옵션리스트를 조회하면 빈 리스트를 리턴한다")
    public void loadOptionListTestReturnEmptyListBecauseWrongStoreId() {
        when(optionMapper.selectOptionList(anyLong())).thenReturn(new ArrayList<>());

        assertEquals(optionService.loadOptionList(12L).isEmpty(), true);

        verify(optionMapper).selectOptionList(anyLong());
    }

    @Test
    @DisplayName("가게 사장님이 메뉴에 대한 옵션을 삭제한다")
    public void deleteOptionListTest() {
        doNothing().when(optionMapper).deleteOptionList(anyList());

        optionService.deleteOptionList(optionList);

        verify(optionMapper).deleteOptionList(anyList());
    }
}
