package com.flab.makedel.mapper;

import com.flab.makedel.annotation.SetDataSource;
import com.flab.makedel.annotation.SetDataSource.DataSourceType;
import com.flab.makedel.dto.StoreInfoDTO;
import com.flab.makedel.dto.StoreDTO;
import java.util.List;

public interface StoreMapper {

    void insertStore(StoreDTO store);

    @SetDataSource(dataSourceType = DataSourceType.SLAVE)
    List<StoreDTO> selectStoreList(String ownerId);

    @SetDataSource(dataSourceType = DataSourceType.SLAVE)
    boolean isMyStore(long storeId, String ownerId);

    @SetDataSource(dataSourceType = DataSourceType.SLAVE)
    StoreDTO selectStore(long storeId, String ownerId);

    void closeMyStore(long storeId);

    void openMyStore(long storeId);

    @SetDataSource(dataSourceType = DataSourceType.SLAVE)
    StoreInfoDTO selectStoreInfo(long storeId);

}
