package com.flab.makedel.controller;

import com.flab.makedel.annotation.CurrentUserId;
import com.flab.makedel.annotation.LoginCheck;
import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.dto.CartItemDTO;
import com.flab.makedel.dto.OrderReceiptDTO;
import com.flab.makedel.dto.PayDTO.PayType;
import com.flab.makedel.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stores/{storeId}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @LoginCheck(userLevel = UserLevel.USER)
    public OrderReceiptDTO registerOrder(@CurrentUserId String userId, @PathVariable long storeId,
        PayType payType) {
        OrderReceiptDTO orderReceipt = orderService.registerOrder(userId, storeId, payType);
        return orderReceipt;
    }
}
