package com.flab.makedel.controller;

import com.flab.makedel.aop.LoginCheck;
import com.flab.makedel.service.LoginService;
import com.flab.makedel.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my-profile")
public class MyProfileController {

    private static final ResponseEntity<Void> RESPONSE_OK = new ResponseEntity(HttpStatus.OK);

    private final LoginService loginService;
    private final UserService userService;

    public MyProfileController(UserService userService, LoginService loginService) {
        this.loginService = loginService;
        this.userService = userService;
    }

    @DeleteMapping
    @LoginCheck
    public ResponseEntity<Void> deleteUser() {
        String userId = loginService.getCurrentUser();
        userService.deleteUser(userId);
        loginService.logoutUser();
        return RESPONSE_OK;
    }

    @PatchMapping("/password")
    @LoginCheck
    public ResponseEntity<Void> changeUserPassword(String password) {
        String userId = loginService.getCurrentUser();
        userService.changeUserPassword(userId, password);
        return RESPONSE_OK;
    }

}
