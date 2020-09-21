package com.flab.makedel.service;

import com.flab.makedel.dto.OptionDTO;
import com.flab.makedel.mapper.OptionMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionMapper optionMapper;

    public void registerOptionList(List<OptionDTO> optionList) {
        optionMapper.insertOptionList(optionList);
    }

    public List<OptionDTO> loadOptionList(long menuId) {
        return optionMapper.selectOptionList(menuId);
    }

    public void deleteOptionList(List<OptionDTO> optionList) {
        optionMapper.deleteOptionList(optionList);
    }

}
