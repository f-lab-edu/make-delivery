package com.flab.makedel.controller;

import com.flab.makedel.annotation.CheckMyStore;
import com.flab.makedel.annotation.CurrentUserId;
import com.flab.makedel.annotation.LoginCheck;
import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.dto.MenuDTO;
import com.flab.makedel.service.MenuService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stores/{storeId}/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    @LoginCheck(userLevel = UserLevel.OWNER)
    @CheckMyStore
    public void insertMenu(MenuDTO menu, @PathVariable Long storeId,
        @CurrentUserId String ownerId) {

        MenuDTO newMenu = menuService.setStoreId(menu, storeId);
        menuService.insertMenu(newMenu);

    }

    @DeleteMapping("/{menuId}")
    @LoginCheck(userLevel = UserLevel.OWNER)
    @CheckMyStore
    public void deleteMenu(@PathVariable Long menuId, @PathVariable Long storeId,
        @CurrentUserId String ownerId) {
        menuService.deleteMenu(menuId);
    }

    @GetMapping
    public ResponseEntity<List<MenuDTO>> loadStoreMenu(@PathVariable Long storeId) {
        List<MenuDTO> menuList = menuService.loadStoreMenu(storeId);
        return ResponseEntity.ok().body(menuList);
    }

}
