package com.flab.makedel.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.config.RedisConfig;
import com.flab.makedel.controller.UserController;
import com.flab.makedel.dto.UserDTO;
import com.flab.makedel.mapper.UserMapper;
import com.flab.makedel.utils.PasswordEncrypter;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
@Import({RedisConfig.class, UserController.class})
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    MockHttpSession mockHttpSession;

    @MockBean
    UserService userService;

    @MockBean
    LoginService loginService;

    @BeforeEach
    void init() {
        mockHttpSession = new MockHttpSession();
    }


    @Test
    @DisplayName("/users로 POST메소드를 보내 회원가입을 요청하는데 성공한다")
    void signUpTest() throws Exception {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("id", "here");
        paramMap.add("password", "123123");
        paramMap.add("name", "newname");
        paramMap.add("phone", "01000000000");
        paramMap.add("email", "mail@mail.com");
        paramMap.add("address", "newaddress");
        paramMap.add("level", "USER");

        mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .params(paramMap))
            .andExpect(status().isOk());

        verify(userService).signUp(any(UserDTO.class));
    }

    @Test
    @DisplayName("/{userId}/exists에 Get메소드로 중복된 아이디인지 체크하고 중복이 아니라면 200 상태코드를 반환한다")
    void checkUniqueIdTest() throws Exception {
        when(userService.isExistsId("newid1")).thenReturn(true);

        mockMvc.perform(
            get("/users/newid1/exists")
        )
            .andExpect(status().isOk());

        verify(userService).isExistsId(any(String.class));
    }

    @Test
    @DisplayName("/{userId}/exists에 Get메소드로 중복된 아이디인지 체크하고 중복이 아니라면 200 상태코드를 반환한다")
    void checkUniqueIdTest_중복() throws Exception {
        when(userService.isExistsId("newid1")).thenReturn(false);

        mockMvc.perform(
            get("/users/newid1/exists")
        )
            .andExpect(status().is4xxClientError());

        verify(userService).isExistsId(any(String.class));

    }

    @Test
    @DisplayName("/login에 Post 메소드로 로그인을 하는데 성공한다")
    void loginTest() throws Exception {
        UserDTO userDTO = UserDTO.builder()
            .id("id")
            .password(PasswordEncrypter.encrypt("pass"))
            .name("name")
            .email("email")
            .phone("phone")
            .address("ad")
            .level(UserLevel.USER)
            .build();

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("id", userDTO.getId());
        paramMap.add("password", "pass");

        Optional<UserDTO> user = Optional.ofNullable(userDTO);
        when(userService.findUserByIdAndPassword(userDTO.getId(), "pass"))
            .thenReturn(user);

        mockMvc.perform(post("/users/login")
            .contentType(MediaType.APPLICATION_JSON)
            .params(paramMap))
            .andExpect(status().isOk());

        verify(userService).findUserByIdAndPassword(any(String.class), any(String.class));
    }

    @Test
    @DisplayName("/login에 Post 메소드로 잘못된 비밀번호로 로그인을 하는데 실패한다")
    void loginTest_실패() throws Exception {

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("id", "sameid");
        paramMap.add("password", "wrong");

        when(userService.findUserByIdAndPassword("sameid", "wrong"))
            .thenReturn(Optional.empty());

        mockMvc.perform(post("/users/login")
            .contentType(MediaType.APPLICATION_JSON)
            .params(paramMap))
            .andExpect(status().is4xxClientError());

        verify(userService).findUserByIdAndPassword(any(String.class), any(String.class));
    }

}
