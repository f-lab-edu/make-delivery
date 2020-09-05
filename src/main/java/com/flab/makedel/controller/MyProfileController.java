package com.flab.makedel.controller;

import static com.flab.makedel.utils.ResponseEntityConstants.RESPONSE_OK;

import com.flab.makedel.annotation.CurrentUserId;
import com.flab.makedel.annotation.LoginCheck;
import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.service.LoginService;
import com.flab.makedel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my-profiles")
@RequiredArgsConstructor
public class MyProfileController {

    private final LoginService loginService;
    private final UserService userService;

    @DeleteMapping
    @LoginCheck(userLevel = UserLevel.USER)
    public ResponseEntity<Void> deleteUser(@CurrentUserId String userId) {
        userService.deleteUser(userId);
        loginService.logoutUser();
        return RESPONSE_OK;
    }

    @PatchMapping("/password")
    @LoginCheck(userLevel = UserLevel.USER)
    public ResponseEntity<Void> changeUserPassword(@CurrentUserId String userId,
        String password) {
        userService.changeUserPassword(userId, password);
        return RESPONSE_OK;
    }

}
