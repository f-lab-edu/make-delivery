package com.flab.makedel.routingdatasource;

import com.flab.makedel.annotation.SetDataSource.DataSourceType;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

@Component
public class RoutingDataSource extends AbstractRoutingDataSource {

    private final DataSource masterDataSource;
    private final DataSource slaveDataSource;

    public RoutingDataSource(@Qualifier(value = "masterDataSource") DataSource masterDataSource,
        @Qualifier(value = "slaveDataSource") DataSource slaveDataSource) {
        this.masterDataSource = masterDataSource;
        this.slaveDataSource = slaveDataSource;

        Map<Object, Object> targetDataSources = new HashMap<>();

        targetDataSources.put(DataSourceType.MASTER, masterDataSource);
        targetDataSources.put(DataSourceType.SLAVE, slaveDataSource);

        super.setTargetDataSources(targetDataSources);
        super.setDefaultTargetDataSource(masterDataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        DataSourceType dataSourceType = RoutingDataSourceManager.getCurrentDataSourceName();
        RoutingDataSourceManager.removeCurrentDataSourceName();
        return dataSourceType;
    }
}
