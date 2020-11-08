
package com.flab.makedel.service;

import static org.mockito.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.dto.UserDTO;
import com.flab.makedel.mapper.UserMapper;
import com.flab.makedel.utils.PasswordEncrypter;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
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

    UserDTO user;



    @BeforeEach
    public void makeUser() {
        user = UserDTO.builder()
            .id("user")
            .password(PasswordEncrypter.encrypt("123"))
            .email("tjdrnr05571@naver.com")
            .name("이성국")
            .phone("010-1234-1234")
            .address("서울시")
            .level(UserLevel.USER)
            .build();
    }

    @Test
    public void signUpTestWhenSuccess() {
        userService.signUp(user);

        verify(userMapper).insertUser(any(UserDTO.class));
    }

    @Test
    public void isExistsIdTestWhenDuplicatedId() {
        when(userMapper.isExistsId(user.getId())).thenReturn(true);

        assertEquals(userService.isExistsId(user.getId()), true);

        verify(userMapper).isExistsId(user.getId());
    }

    @Test
    public void isExistsIdTestWhenNotDuplicatedId() {
        when(userMapper.isExistsId(user.getId())).thenReturn(false);

        assertEquals(userService.isExistsId(user.getId()), false);

        verify(userMapper).isExistsId(user.getId());
    }

    @Test
    public void deleteUserTestWhenSuccess() {
        userService.deleteUser(user.getId());

        verify(userMapper).deleteUser(user.getId());
    }

    @Test
    public void changeUserPasswordTestWhenSuccess() {
        userService.changeUserPassword(user.getId(), "123");

        verify(userMapper).updateUserPassword(any(String.class), any(String.class));
    }

    @Test
    public void findUserByIdAndPasswordTestWhenSuccess() {
        when(userMapper.selectUserById(user.getId())).thenReturn(user);

        assertEquals(userService.findUserByIdAndPassword(user.getId(), "123"),
            Optional.ofNullable(user));

        verify(userMapper).selectUserById(user.getId());
    }

    @Test
    public void findUserByIdAndPasswordTestWhenFail() {
        when(userMapper.selectUserById(user.getId())).thenReturn(null);

        assertEquals(userService.findUserByIdAndPassword(user.getId(), user.getPassword()),
            Optional.empty());

        verify(userMapper).selectUserById(any(String.class));
    }


}