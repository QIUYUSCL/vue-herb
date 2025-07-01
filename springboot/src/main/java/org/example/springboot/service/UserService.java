package org.example.springboot.service;

import jakarta.annotation.Resource;
import org.example.springboot.entity.UserInfo;
import org.example.springboot.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public List<UserInfo> selectAll() {
        return userMapper.selectAll();
    }

    public UserInfo selectById(Integer user_id) {
        return userMapper.selectById(user_id);
    }

    public UserInfo login(String username, String password) {
        return userMapper.selectByUsernameAndPassword(username, password);
    }
}
