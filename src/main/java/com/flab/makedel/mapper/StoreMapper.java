package com.flab.makedel.mapper;

import com.flab.makedel.dto.StoreDTO;
import java.util.List;

public interface StoreMapper {

    void insertStore(StoreDTO store);

    List<StoreDTO> selectStoreList(String ownerId);

    boolean isMyStore(int storeId, String ownerId);

    StoreDTO selectStore(int storeId, String ownerId);

    void closeMyStore(int storeId);

    void openMyStore(int storeId);

}
