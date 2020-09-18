package com.flab.makedel.mapper;

import com.flab.makedel.dto.StoreDTO;
import java.util.List;

public interface StoreMapper {

    void insertStore(StoreDTO store);

    List<StoreDTO> selectStoreList(String ownerId);

    boolean isMyStore(long storeId, String ownerId);

    StoreDTO selectStore(long storeId, String ownerId);

    void closeMyStore(long storeId);

    void openMyStore(long storeId);

}
