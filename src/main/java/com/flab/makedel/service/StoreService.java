package com.flab.makedel.service;

import com.flab.makedel.dto.StoreDTO;
import com.flab.makedel.mapper.StoreMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreMapper storeMapper;

    public void insertStore(StoreDTO store) {
        storeMapper.insertStore(store);
    }

    public StoreDTO setOwnerID(StoreDTO store, String ownerId) {
        StoreDTO newStore = StoreDTO.builder()
            .name(store.getName())
            .phone(store.getPhone())
            .address(store.getAddress())
            .ownerId(ownerId)
            .introduction(store.getIntroduction())
            .categoryId(store.getCategoryId())
            .build();
        return newStore;
    }

    public List<StoreDTO> getMyAllStore(String ownerId) {
        return storeMapper.selectStoreList(ownerId);
    }

    public StoreDTO getMyStore(long storeId, String ownerId) {
        return storeMapper.selectStore(storeId, ownerId);
    }

    public boolean isMyStore(long storeId, String ownerId) {
        return storeMapper.isMyStore(storeId, ownerId);
    }

    public void closeMyStore(long storeId) {
        storeMapper.closeMyStore(storeId);
    }

    public void openMyStore(long storeId) {
        storeMapper.openMyStore(storeId);
    }

    public void validateMyStore(long storeId, String ownerId) throws HttpClientErrorException {
        boolean isMyStore = isMyStore(storeId, ownerId);
        if (!isMyStore) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }

}
