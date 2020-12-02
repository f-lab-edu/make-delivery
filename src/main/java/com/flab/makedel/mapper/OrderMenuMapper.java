package com.flab.makedel.mapper;

import com.flab.makedel.annotation.SetDataSource;
import com.flab.makedel.annotation.SetDataSource.DataSourceType;
import com.flab.makedel.dto.OrderMenuDTO;
import java.util.List;

public interface OrderMenuMapper {

    @SetDataSource(dataSourceType = DataSourceType.MASTER)
    void insertOrderMenu(List<OrderMenuDTO> orderMenuList);

}
