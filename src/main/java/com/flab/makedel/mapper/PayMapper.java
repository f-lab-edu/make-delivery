package com.flab.makedel.mapper;

import com.flab.makedel.annotation.SetDataSource;
import com.flab.makedel.annotation.SetDataSource.DataSourceType;
import com.flab.makedel.dto.PayDTO;

public interface PayMapper {

    @SetDataSource(dataSourceType = DataSourceType.MASTER)
    void insertPay(PayDTO payDTO);

}
