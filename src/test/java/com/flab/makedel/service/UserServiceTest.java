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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("회원가입에 성공합니다")
    public void signUpTestWhenSuccess() {
        when(userMapper.isExistsId(user.getId())).thenReturn(false);
        doNothing().when(userMapper).insertUser(any(UserDTO.class));

        userService.signUp(user);

        verify(userMapper).insertUser(any(UserDTO.class));
    }

    @Test
    @DisplayName("회원가입에 실패합니다 : 중복된 아이디")
    public void signUpTestWhenFail() {
        when(userMapper.isExistsId(user.getId())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.signUp(user));

        verify(userMapper).isExistsId(user.getId());
    }

    @Test
    @DisplayName("아이디 중복 확인을 합니다")
    public void isExistsIdTestWhenReturnTrue() {
        when(userMapper.isExistsId(user.getId())).thenReturn(true);

        assertEquals(userService.isExistsId(user.getId()), true);

        verify(userMapper).isExistsId(user.getId());
    }

    @Test
    @DisplayName("유저 삭제합니다")
    public void deleteUserTestWhenSuccess() {
        when(userMapper.isExistsId(user.getId())).thenReturn(true);
        doNothing().when(userMapper).deleteUser(user.getId());

        userService.deleteUser(user.getId());

        verify(userMapper).deleteUser(user.getId());
    }

    @Test
    @DisplayName("유저 삭제에 실패합니다 : 삭제할 아이디 존재하지 않음")
    public void deleteUserTestWhenFail() {
        when(userMapper.isExistsId(user.getId())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> userService.deleteUser(user.getId()));

        verify(userMapper).isExistsId(user.getId());
    }

    @Test
    @DisplayName("유저 비밀번호를 변경합니")
    public void changeUserPasswordTestWhenSuccess() {
        doNothing().when(userMapper).updateUserPassword(any(String.class), any(String.class));

        userService.changeUserPassword(user.getId(), "123");

        verify(userMapper).updateUserPassword(any(String.class), any(String.class));
    }

    @Test
    @DisplayName("아이디와 비밀번호로 유저를 찾습니다")
    public void findUserByIdAndPasswordTestWhenSuccess() {
        when(userMapper.selectUserById(user.getId())).thenReturn(user);

        assertEquals(userService.findUserByIdAndPassword(user.getId(), "123"),
            Optional.ofNullable(user));

        verify(userMapper).selectUserById(user.getId());
    }

    @Test
    @DisplayName("아이디와 비밀번호로 유저 찾기에 실패합니다 : 주어진 유저 아이디 존재하지 않음")
    public void findUserByIdAndPasswordTestWhenFailBecauseNotExistId() {
        when(userMapper.selectUserById(user.getId())).thenReturn(null);

        assertEquals(userService.findUserByIdAndPassword(user.getId(), user.getPassword()),
            Optional.empty());

        verify(userMapper).selectUserById(any(String.class));
    }

    @Test
    @DisplayName("아이디와 비밀번호로 유저 찾기에 실패합니다 : 주어진 유저 비밀번호 오류")
    public void findUserByIdAndPasswordTestWhenFailBecausePasswordError() {
        when(userMapper.selectUserById(user.getId())).thenReturn(user);

        assertEquals(userService.findUserByIdAndPassword(user.getId(), "not same password"),
            Optional.empty());

        verify(userMapper).selectUserById(any(String.class));
    }

}