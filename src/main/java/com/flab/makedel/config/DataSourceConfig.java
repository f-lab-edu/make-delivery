package com.flab.makedel.config;

import com.flab.makedel.annotation.SetDataSource.DataSourceType;
import com.flab.makedel.routingdatasource.RoutingDataSourceManager;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Configuration
@PropertySource("classpath:/application-dev.properties")
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource routingDataSource(
        @Qualifier(value = "masterDataSource") DataSource masterDataSource,
        @Qualifier(value = "slaveDataSource") DataSource slaveDataSource) {

        AbstractRoutingDataSource routingDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                DataSourceType dataSourceType = RoutingDataSourceManager.getCurrentDataSourceName();

                if (TransactionSynchronizationManager
                    .isActualTransactionActive()) {
                    boolean readOnly = TransactionSynchronizationManager
                        .isCurrentTransactionReadOnly();
                    if (readOnly) {
                        dataSourceType = DataSourceType.SLAVE;
                    } else {
                        dataSourceType = DataSourceType.MASTER;
                    }
                }

                RoutingDataSourceManager.removeCurrentDataSourceName();
                return dataSourceType;
            }
        };

        Map<Object, Object> targetDataSources = new HashMap<>();

        targetDataSources.put(DataSourceType.MASTER, masterDataSource);
        targetDataSources.put(DataSourceType.SLAVE, slaveDataSource);

        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;
    }

    @Bean
    public DataSource lazyRoutingDataSource(
        @Qualifier(value = "routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(
        @Qualifier(value = "lazyRoutingDataSource") DataSource lazyRoutingDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(lazyRoutingDataSource);
        return transactionManager;
    }
}
