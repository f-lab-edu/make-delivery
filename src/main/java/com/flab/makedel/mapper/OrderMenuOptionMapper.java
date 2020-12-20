package com.flab.makedel.mapper;

import com.flab.makedel.annotation.SetDataSource;
import com.flab.makedel.annotation.SetDataSource.DataSourceType;
import com.flab.makedel.dto.OrderMenuOptionDTO;
import java.util.List;

public interface OrderMenuOptionMapper {

    @SetDataSource(dataSourceType = DataSourceType.MASTER)
    void insertOrderMenuOption(List<OrderMenuOptionDTO> orderMenuOptionList);

}
