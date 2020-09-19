package com.flab.makedel.controller;

import com.flab.makedel.annotation.CurrentUserId;
import com.flab.makedel.annotation.LoginCheck;
import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.dto.MenuDTO;
import com.flab.makedel.service.MenuService;
import com.flab.makedel.service.StoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/stores/{storeId}/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final StoreService storeService;

    @PostMapping
    @LoginCheck(userLevel = UserLevel.OWNER)
    public void insertMenu(MenuDTO menu, @PathVariable long storeId,
        @CurrentUserId String ownerId) {

        storeService.validateMyStore(storeId, ownerId);
        MenuDTO newMenu = menuService.setStoreId(menu, storeId);
        menuService.insertMenu(newMenu);

    }

    @DeleteMapping("/{menuId}")
    @LoginCheck(userLevel = UserLevel.OWNER)
    public void deleteMenu(@PathVariable Long menuId, @PathVariable long storeId,
        @CurrentUserId String ownerId) {

        storeService.validateMyStore(storeId, ownerId);
        menuService.deleteMenu(menuId);
        
    }

    @GetMapping
    public ResponseEntity<List<MenuDTO>> loadStoreMenu(@PathVariable long storeId) {
        List<MenuDTO> menuList = menuService.loadStoreMenu(storeId);
        return ResponseEntity.ok().body(menuList);
    }

}
