package com.flab.makedel.service;

import com.flab.makedel.dao.CartItemDAO;
import com.flab.makedel.dto.CartItemDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemDAO cartItemDAO;

    public List<CartItemDTO> loadCart(String userId) {
        return cartItemDAO.selectCartList(userId);
    }

    public void registerMenuInCart(String userId, CartItemDTO cart) {
        cartItemDAO.insertMenu(userId, cart);
    }

    public void insertMenuList(String userId, List<CartItemDTO> cartList) {
        cartItemDAO.insertMenuList(userId, cartList);
    }

    public void deleteAllMenuInCart(String userId) {
        cartItemDAO.deleteMenuList(userId);
    }

}
