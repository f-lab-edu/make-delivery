package com.flab.makedel.controller;

import com.flab.makedel.annotation.LoginCheck;
import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.dto.OrderReceiptDTO;
import com.flab.makedel.dto.RiderDTO;
import com.flab.makedel.service.DeliveryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders", params = "status")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;


    @GetMapping(params = "orderId")
    @LoginCheck(userLevel = UserLevel.RIDER)
    public OrderReceiptDTO loadStandbyOrder(@RequestParam long orderId, String riderAddress) {
        return deliveryService.loadStandbyOrder(orderId, riderAddress);
    }

    @GetMapping
    @LoginCheck(userLevel = UserLevel.RIDER)
    public List<String> loadStandbyOrderList(String riderAddress) {
        return deliveryService.loadStandbyOrderList(riderAddress);
    }

}
