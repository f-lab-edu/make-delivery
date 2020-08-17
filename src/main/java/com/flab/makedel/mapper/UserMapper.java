package com.flab.makedel.mapper;

import com.flab.makedel.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    boolean isExistsId(String id);

    void insertUser(UserDTO user);
}
