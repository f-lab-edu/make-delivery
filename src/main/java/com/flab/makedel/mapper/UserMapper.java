package com.flab.makedel.mapper;

import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.dto.UserDTO;
import com.flab.makedel.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    boolean isExistsId(String id);

    UserInfoDTO selectUserInfo(String id);

    void insertUser(UserDTO user);

    UserDTO selectUserById(String id);

    void deleteUser(String id);

    void updateUserPassword(String id, String newPassword);

}
