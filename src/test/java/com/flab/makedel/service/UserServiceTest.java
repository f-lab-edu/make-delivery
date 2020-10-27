package com.flab.makedel.service;

import static org.mockito.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.dto.UserDTO;
import com.flab.makedel.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    @Test
    void signUpTestWhenSuccess() {

        UserDTO user = UserDTO.builder()
            .id("user")
            .password("123")
            .email("tjdrnr05571@naver.com")
            .name("이성국")
            .phone("010-1234-1234")
            .address("서울시")
            .level(UserLevel.USER)
            .build();

        userService.signUp(user);

        verify(userMapper).insertUser(any(UserDTO.class));

    }

}