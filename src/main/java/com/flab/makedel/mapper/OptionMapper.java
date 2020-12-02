package com.flab.makedel.mapper;

import com.flab.makedel.annotation.SetDataSource;
import com.flab.makedel.annotation.SetDataSource.DataSourceType;
import com.flab.makedel.dto.OptionDTO;
import java.util.List;

/*
    bulk insert를 사용한 이유 : 쿼리를 날려 DB에 데이터를 넣으면 쿼리 전후로 이루어지는 여러 작업들이 있다.
    (connecting, sending query to server, parsing query, inserting indexes,closing등)
    bulk insert를 사용하여 한번의 쿼리를 날려 여러개의 데이터를 넣으면 쿼리 전후로 이루어지는 작업들이
    한번만 이루어지면 된다. 즉 여러개의 데이터가 하나의 transaction에서 처리가 된다. 만약 여러 개의 데이터중
    한개라도 에러가 난다면 Rollback이 이루어진다.
    500개의 데이터를 넣는다고 가정하면
    bulk insert를 사용 하지 않으면 500번의 insert가 이루어져 커넥션이 500번 일어난 것이다.
 */

public interface OptionMapper {

    @SetDataSource(dataSourceType = DataSourceType.MASTER)
    void insertOptionList(List<OptionDTO> optionList);

    @SetDataSource(dataSourceType = DataSourceType.MASTER)
    void deleteOptionList(List<OptionDTO> optionList);

    @SetDataSource(dataSourceType = DataSourceType.SLAVE)
    List<OptionDTO> selectOptionList(long menuId);

}
