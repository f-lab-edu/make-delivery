package com.flab.makedel.aop;

import com.flab.makedel.annotation.SetDataSource;
import com.flab.makedel.annotation.SetDataSource.DataSourceType;
import com.flab.makedel.exception.WrongDataSourceException;
import com.flab.makedel.routingdatasource.RoutingDataSourceManager;
import java.sql.SQLException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SetDataSourceAspect {

    @Before("@annotation(com.flab.makedel.annotation.SetDataSource) && @annotation(target)")
    public void setDatasource(SetDataSource target) throws SQLException {
        if (target.dataSourceType() == DataSourceType.MASTER
            || target.dataSourceType() == DataSourceType.SLAVE) {
            RoutingDataSourceManager.setCurrentDataSourceName(target.dataSourceType());
        } else {
            throw new WrongDataSourceException("Wrong DataSource Type : Should Check Exception");
        }
    }
}
