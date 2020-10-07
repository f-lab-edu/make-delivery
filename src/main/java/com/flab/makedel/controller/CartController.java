package com.flab.makedel.controller;

import com.flab.makedel.annotation.LoginCheck;
import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.dto.CartItemDTO;
import com.flab.makedel.service.CartService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    @LoginCheck(userLevel = UserLevel.USER)
    public void registerMenuInCart(@PathVariable String userId,
        @Valid @RequestBody CartItemDTO cart) {
        cartService.registerMenuInCart(userId, cart);
    }

    @PostMapping("/temp")
    public void dfdf(@PathVariable String userId,
        @RequestBody List<CartItemDTO> cartList) {
        cartService.insertMenuList(userId, cartList);
    }


    @GetMapping
    @LoginCheck(userLevel = UserLevel.USER)
    public List<CartItemDTO> loadCart(@PathVariable String userId) {
        List<CartItemDTO> cartList = cartService.loadCart(userId);
        return cartList;
    }

    @DeleteMapping
    @LoginCheck(userLevel = UserLevel.USER)
    public void deleteAllMenuInCart(@PathVariable String userId) {
        cartService.deleteAllMenuInCart(userId);
    }

}
