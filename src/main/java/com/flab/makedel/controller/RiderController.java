package com.flab.makedel.controller;

import com.flab.makedel.annotation.LoginCheck;
import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.dto.RiderDTO;
import com.flab.makedel.service.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/riders/{riderId}")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;

    @PostMapping("/standby")
    @LoginCheck(userLevel = UserLevel.RIDER)
    public void registerStandbyRider(RiderDTO rider) {
        riderService.registerStandbyRider(rider);
    }

}
