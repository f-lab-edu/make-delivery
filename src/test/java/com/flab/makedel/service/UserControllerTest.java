package com.flab.makedel.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.config.RedisConfig;
import com.flab.makedel.controller.UserController;
import com.flab.makedel.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
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
}
