package com.flab.makedel.mapper;

import com.flab.makedel.dto.StoreCategoryDTO;
import java.util.List;

public interface StoreCategoryMapper {

    List<StoreCategoryDTO> selectCategoryList();

}
