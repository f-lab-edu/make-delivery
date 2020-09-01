package com.flab.makedel.controller;

import static com.flab.makedel.utils.ResponseEntityConstants.RESPONSE_OK;

import com.flab.makedel.aop.LoginCheck;
import com.flab.makedel.aop.SessionId;
import com.flab.makedel.dto.CurrentUserDTO;
import com.flab.makedel.service.LoginService;
import com.flab.makedel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @LoginCheck
    public ResponseEntity<Void> deleteUser(@SessionId CurrentUserDTO currentUser) {
        userService.deleteUser(currentUser.getId());
        loginService.logoutUser();
        return RESPONSE_OK;
    }

    @PatchMapping("/password")
    @LoginCheck
    public ResponseEntity<Void> changeUserPassword(@SessionId CurrentUserDTO currentUser,
        String password) {
        userService.changeUserPassword(currentUser.getId(), password);
        return RESPONSE_OK;
    }

}
