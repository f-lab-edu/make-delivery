package com.flab.makedel.mapper;

import com.flab.makedel.annotation.SetDataSource;
import com.flab.makedel.annotation.SetDataSource.DataSourceType;
import com.flab.makedel.dto.MenuDTO;
import java.util.List;

public interface MenuMapper {

    @SetDataSource(dataSourceType = DataSourceType.MASTER)
    void insertMenu(MenuDTO menu);

    @SetDataSource(dataSourceType = DataSourceType.MASTER)
    void deleteMenu(long menuId);

    @SetDataSource(dataSourceType = DataSourceType.SLAVE)
    List<MenuDTO> selectStoreMenu(long storeId);

}
