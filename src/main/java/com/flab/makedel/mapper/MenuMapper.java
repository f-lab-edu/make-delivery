package com.flab.makedel.mapper;

import com.flab.makedel.dto.MenuDTO;
import java.util.List;

public interface MenuMapper {

    void insertMenu(MenuDTO menu);

    void deleteMenu(long menuId);

    List<MenuDTO> selectStoreMenu(long storeId);

}
