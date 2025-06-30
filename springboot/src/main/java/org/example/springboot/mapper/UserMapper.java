package org.example.springboot.mapper;

import org.example.springboot.entity.User;

import java.util.List;

public interface UserMapper {
    List<User> selectAll();

    User selectById(Integer id);

}
