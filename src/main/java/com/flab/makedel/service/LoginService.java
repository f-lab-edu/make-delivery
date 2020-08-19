package com.flab.makedel.service;

import com.flab.makedel.dto.UserDTO;
import com.flab.makedel.mapper.UserMapper;
import com.flab.makedel.utils.PasswordEncrypter;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserMapper userMapper;

    public LoginService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserDTO findUserByIdAndPassword(String id, String password) {
        UserDTO user = userMapper.selectUserById(id);

        if (user == null) {
            return null;
        }

        boolean isSamePassword = PasswordEncrypter.isMatch(password, user.getPassword());

        if (!isSamePassword) {
            return null;
        }

        return user;
    }
}
