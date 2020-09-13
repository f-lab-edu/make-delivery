package com.flab.makedel.controller;

import static com.flab.makedel.utils.ResponseEntityConstants.RESPONSE_OK;

import com.flab.makedel.annotation.CurrentUserId;
import com.flab.makedel.annotation.LoginCheck;
import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.dto.StoreDTO;
import com.flab.makedel.service.StoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    @LoginCheck(userLevel = UserLevel.OWNER)
    public ResponseEntity<Void> insertStore(StoreDTO store, @CurrentUserId String ownerId) {

        StoreDTO newStore = storeService.setOwnerID(store, ownerId);
        storeService.insertStore(newStore);

        return RESPONSE_OK;

    }

    @GetMapping
    @LoginCheck(userLevel = UserLevel.OWNER)
    public ResponseEntity<List<StoreDTO>> getMyAllStore(@CurrentUserId String ownerId) {

        List<StoreDTO> stores = storeService.getMyAllStore(ownerId);
        return ResponseEntity.ok().body(stores);

    }

    @GetMapping("/{storeId}")
    @LoginCheck(userLevel = UserLevel.OWNER)
    public ResponseEntity<StoreDTO> getMyStore(@PathVariable int storeId,
        @CurrentUserId String ownerId) {

        validateMyStore(storeId, ownerId);

        StoreDTO store = storeService.getMyStore(storeId, ownerId);
        return ResponseEntity.ok().body(store);

    }

    @PatchMapping("/{storeId}/closed")
    @LoginCheck(userLevel = UserLevel.OWNER)
    public ResponseEntity<Void> closeMyStore(@PathVariable int storeId,
        @CurrentUserId String ownerId) {

        validateMyStore(storeId, ownerId);

        storeService.closeMyStore(storeId);
        return RESPONSE_OK;

    }

    @PatchMapping("/{storeId}/opened")
    @LoginCheck(userLevel = UserLevel.OWNER)
    public ResponseEntity<Void> openMyStore(@PathVariable int storeId,
        @CurrentUserId String ownerId) {

        validateMyStore(storeId, ownerId);

        storeService.openMyStore(storeId);
        return RESPONSE_OK;

    }

    private void validateMyStore(int storeId, String ownerId) throws HttpClientErrorException {
        boolean isMyStore = storeService.isMyStore(storeId, ownerId);
        if (!isMyStore) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }

}
