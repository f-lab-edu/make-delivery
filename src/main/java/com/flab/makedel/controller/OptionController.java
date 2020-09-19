package com.flab.makedel.controller;

import com.flab.makedel.annotation.CurrentUserId;
import com.flab.makedel.annotation.LoginCheck;
import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.dto.OptionDTO;
import com.flab.makedel.service.OptionService;
import com.flab.makedel.service.StoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/stores/{storeId}/menus/{menuId}/options")
@RequiredArgsConstructor
public class OptionController {

    private final OptionService optionService;
    private final StoreService storeService;

    @PostMapping
    @LoginCheck(userLevel = UserLevel.OWNER)
    public void registerOptionList(@RequestBody List<OptionDTO> optionList,
        @PathVariable long storeId, @CurrentUserId String ownerId) {

        boolean isMyStore = storeService.isMyStore(storeId, ownerId);
        if (!isMyStore) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }

        optionService.registerOptionList(optionList);

    }

    @GetMapping
    public ResponseEntity<List<OptionDTO>> loadOptionList(@PathVariable long menuId) {
        List<OptionDTO> optionList = optionService.loadOptionList(menuId);
        return ResponseEntity.ok().body(optionList);
    }

    @DeleteMapping
    @LoginCheck(userLevel = UserLevel.OWNER)
    public void deleteOptionList(@RequestBody List<OptionDTO> optionList,
        @PathVariable long storeId, @CurrentUserId String ownerId) {

        boolean isMyStore = storeService.isMyStore(storeId, ownerId);
        if (!isMyStore) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }

        optionService.deleteOptionList(optionList);

    }

}
