package com.flab.makedel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SetDataSource {

    DataSourceType dataSourceType();

    enum DataSourceType {
        MASTER, SLAVE;

        public static String getDataSourceTypeToString(DataSourceType dataSourceType) {
            return dataSourceType.name();
        }
    }

}
