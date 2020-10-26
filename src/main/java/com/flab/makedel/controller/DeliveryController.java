package com.flab.makedel.controller;

import com.flab.makedel.annotation.LoginCheck;
import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.dto.OrderReceiptDTO;
import com.flab.makedel.service.DeliveryService;
import com.flab.makedel.service.RiderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/standby/orders")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/{orderId}")
    @LoginCheck(userLevel = UserLevel.RIDER)
    public OrderReceiptDTO loadStandbyOrder(@PathVariable long orderId) {
        return deliveryService.loadStandbyOrder(orderId);
    }

    @GetMapping
    @LoginCheck(userLevel = UserLevel.RIDER)
    public List<String> loadStandbyOrderList() {
        return deliveryService.loadStandbyOrderList();
    }

    @DeleteMapping("/{orderId}")
    @LoginCheck(userLevel = UserLevel.RIDER)
    public void deleteStandbyOrder(@PathVariable long orderId) {
        deliveryService.deleteStandbyOrder(orderId);
    }

}
