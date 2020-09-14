package com.flab.makedel.service;

import com.flab.makedel.dto.StoreCategoryDTO;
import com.flab.makedel.mapper.StoreCategoryMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreCategoryService {

    private final StoreCategoryMapper storeCategoryMapper;

    @Cacheable(value = "categories", key = "'store_category'")
    public List<StoreCategoryDTO> getAllStoreCategory() {
        return storeCategoryMapper.selectCategoryList();
    }

}
