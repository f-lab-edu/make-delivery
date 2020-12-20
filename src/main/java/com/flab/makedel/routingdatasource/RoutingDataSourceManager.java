package com.flab.makedel.routingdatasource;

import com.flab.makedel.annotation.SetDataSource.DataSourceType;

public class RoutingDataSourceManager {

    private static final ThreadLocal<DataSourceType> currentDataSourceName = new ThreadLocal<>();

    public static void setCurrentDataSourceName(DataSourceType dataSourceType) {
        currentDataSourceName.set(dataSourceType);
    }

    public static DataSourceType getCurrentDataSourceName() {
        return currentDataSourceName.get();
    }

    public static void removeCurrentDataSourceName() {
        currentDataSourceName.remove();
    }
}
