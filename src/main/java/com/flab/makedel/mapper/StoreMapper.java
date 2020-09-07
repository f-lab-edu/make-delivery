package com.flab.makedel.mapper;

import com.flab.makedel.dto.StoreDTO;
import java.util.List;

public interface StoreMapper {

    void insertStore(StoreDTO store);

    List<StoreDTO> selectStoreList(String ownerId);

    boolean isMyStore(Long storeId, String ownerId);

    StoreDTO selectStore(Long storeId, String ownerId);

    void closeMyStore(Long storeId);

    void openMyStore(Long storeId);

}
