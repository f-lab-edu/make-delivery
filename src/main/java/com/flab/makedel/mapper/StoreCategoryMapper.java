package com.flab.makedel.mapper;

import com.flab.makedel.dto.StoreCategoryDTO;
import java.util.List;

/*
    selectCategoryList로 가져오는 List는 운영자들이 카테고리를 정하기 때문에
    많아야 20개 내외이고 항상 같은 리스트를 리턴해주기 때문에 모든 데이터를 호출하도록 했습니다.
    또한 서비스에서 이 mapper를 호출하기전 캐시를 사용하기 때문에 호출이 잦지 않습니다.
 */

public interface StoreCategoryMapper {

    List<StoreCategoryDTO> selectCategoryList();

}
