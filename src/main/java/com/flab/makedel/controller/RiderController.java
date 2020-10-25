package com.flab.makedel.controller;

import com.flab.makedel.annotation.LoginCheck;
import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.dto.RiderDTO;
import com.flab.makedel.service.RiderService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
        riderService.registerStandbyRider(rider);
    }

    @DeleteMapping("/{riderId}/standby")
    @LoginCheck(userLevel = UserLevel.RIDER)
    public void deleteStandbyRider(RiderDTO rider) {
        riderService.deleteStandbyRider(rider);
    }

    @GetMapping("/{riderId}/standby")
    public RiderDTO loadStandbyRiderInfo(@PathVariable String riderId) {
        return riderService.loadStandbyRiderInfo(riderId);
    }

    @GetMapping("/standby")
    public Set<String> loadStandbyRiderList() {
        return riderService.loadStandbyRiderList();
    }
}
