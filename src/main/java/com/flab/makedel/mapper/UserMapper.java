package com.flab.makedel.mapper;

import com.flab.makedel.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    boolean isExistsId(String id);

    void insertUser(UserDTO user);

    String selectPassword(String id);

    String selectId(String id);

    UserDTO selectUser(String id, String password);
}
