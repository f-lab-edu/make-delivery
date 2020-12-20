package com.flab.makedel.mapper;

import com.flab.makedel.annotation.LoginCheck.UserLevel;
import com.flab.makedel.annotation.SetDataSource;
import com.flab.makedel.annotation.SetDataSource.DataSourceType;
import com.flab.makedel.dto.UserDTO;
import com.flab.makedel.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @SetDataSource(dataSourceType = DataSourceType.SLAVE)
    boolean isExistsId(String id);

    @SetDataSource(dataSourceType = DataSourceType.SLAVE)
    UserInfoDTO selectUserInfo(String id);

    @SetDataSource(dataSourceType = DataSourceType.MASTER)
    void insertUser(UserDTO user);

    @SetDataSource(dataSourceType = DataSourceType.SLAVE)
    UserDTO selectUserById(String id);

    @SetDataSource(dataSourceType = DataSourceType.MASTER)
    void deleteUser(String id);

    @SetDataSource(dataSourceType = DataSourceType.MASTER)
    void updateUserPassword(String id, String newPassword);

}
