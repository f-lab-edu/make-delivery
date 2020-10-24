package com.flab.makedel.controller;

import com.flab.makedel.dto.OrderReceiptDTO;
import com.flab.makedel.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/{orderId}/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping
    public OrderReceiptDTO loadStandbyOrder(@PathVariable long orderId) {
        return deliveryService.loadStandbyOrder(orderId);
    }
}
