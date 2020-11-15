package com.flab.makedel.service;

import static org.mockito.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.dto.UserDTO;
import com.flab.makedel.mapper.UserMapper;
import com.flab.makedel.utils.PasswordEncrypter;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
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
        when(userMapper.isExistsId(user.getId())).thenReturn(false);
        doNothing().when(userMapper).insertUser(any(UserDTO.class));

        userService.signUp(user);

        verify(userMapper).insertUser(any(UserDTO.class));
    }

    @Test
    public void signUpTestWhenFail_유저_아이디_중복() {
        when(userMapper.isExistsId(user.getId())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.signUp(user));

        verify(userMapper).isExistsId(user.getId());
    }

    @Test
    public void isExistsIdTestWhenFail_유저_아이디_중복() {
        when(userMapper.isExistsId(user.getId())).thenReturn(true);

        assertEquals(userService.isExistsId(user.getId()), true);

        verify(userMapper).isExistsId(user.getId());
    }

    @Test
    public void isExistsIdTestWhenSuccess() {
        when(userMapper.isExistsId(user.getId())).thenReturn(false);

        assertEquals(userService.isExistsId(user.getId()), false);

        verify(userMapper).isExistsId(user.getId());
    }

    @Test
    public void deleteUserTestWhenSuccess() {
        when(userMapper.isExistsId(user.getId())).thenReturn(true);
        doNothing().when(userMapper).deleteUser(user.getId());

        userService.deleteUser(user.getId());

        verify(userMapper).deleteUser(user.getId());
    }

    @Test
    public void deleteUserTestWhenFail_유저_아이디_없음() {
        when(userMapper.isExistsId(user.getId())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.deleteUser(user.getId()));

        verify(userMapper).isExistsId(user.getId());
    }

    @Test
    public void changeUserPasswordTestWhenSuccess() {
        doNothing().when(userMapper).updateUserPassword(any(String.class), any(String.class));

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
    public void findUserByIdAndPasswordTestWhenFail_유저_아이디_없음() {
        when(userMapper.selectUserById(user.getId())).thenReturn(null);

        assertEquals(userService.findUserByIdAndPassword(user.getId(), user.getPassword()),
            Optional.empty());

        verify(userMapper).selectUserById(any(String.class));
    }

    @Test
    public void findUserByIdAndPasswordTestWhenFail_유저_비밀번호_오류() {
        when(userMapper.selectUserById(user.getId())).thenReturn(user);

        assertEquals(userService.findUserByIdAndPassword(user.getId(), "not same password"),
            Optional.empty());

        verify(userMapper).selectUserById(any(String.class));
    }

}