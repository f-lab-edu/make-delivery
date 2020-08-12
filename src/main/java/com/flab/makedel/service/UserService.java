package com.flab.makedel.service;

import com.flab.makedel.dto.UserDTO;
import com.flab.makedel.mapper.UserMapper;
import com.flab.makedel.utils.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
@Service 어노테이션은 비즈니스 로직을 처리하는 서비스라는 것을 알려주는 어노테이션이다.
Component Scan을 통하여 @Service 어노테이션이 붙은 클래스를 스프링이 빈으로 등록하고 이 빈의 라이프사이클을 관리한다.
*/
@Service
public class UserService {

    private UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper=userMapper;
    }

    public void signUp(UserDTO user) {
        user.setPassword(PasswordEncrypter.encrypt(user.getPassword()));
        userMapper.insertUser(user);
    }

    public boolean checkUniqueId(String id) {
        return userMapper.checkUniqueId(id) == 0;
    }

}

