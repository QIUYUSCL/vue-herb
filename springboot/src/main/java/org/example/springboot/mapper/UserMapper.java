package org.example.springboot.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.springboot.entity.UserInfo;

import java.util.List;

public interface UserMapper {
    List<UserInfo> selectAll();

    UserInfo selectById(Integer user_id);

    UserInfo selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    int insertUser(UserInfo userInfo);
}
