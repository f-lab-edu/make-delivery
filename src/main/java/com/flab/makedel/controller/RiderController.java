package com.flab.makedel.controller;

import com.flab.makedel.annotation.LoginCheck;
import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.dto.RiderDTO;
import com.flab.makedel.service.RiderService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/riders")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;

    @PostMapping("/{riderId}/standby")
    @LoginCheck(userLevel = UserLevel.RIDER)
    public void registerStandbyRider(RiderDTO rider) {
        riderService.registerStandbyRiderWhenStartWork(rider);
    }

    @DeleteMapping("/{riderId}/standby")
    @LoginCheck(userLevel = UserLevel.RIDER)
    public void deleteStandbyRider(RiderDTO rider) {
        riderService.deleteStandbyRiderWhenStopWork(rider);
    }

    @PatchMapping("/{riderId}/orders/{orderId}/accept")
    @LoginCheck(userLevel = UserLevel.RIDER)
    public void acceptStandbyOrder(@PathVariable long orderId,
        RiderDTO rider) {
        riderService.acceptStandbyOrder(orderId, rider);
    }

    @PatchMapping("/{riderId}/orders/{orderId}/finish")
    @LoginCheck(userLevel = UserLevel.RIDER)
    public void finishDeliveringOrder(@PathVariable long orderId, RiderDTO rider) {
        riderService.finishDeliveringOrder(orderId, rider);
    }

}
