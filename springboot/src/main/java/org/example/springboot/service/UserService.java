package org.example.springboot.service;

import jakarta.annotation.Resource;
import org.example.springboot.entity.*;
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

    public int registerUser(UserInfo userInfo) {
        return userMapper.insertUser(userInfo);
    }

    /**
     * 通过 user_id 获取用户评论
     * @param userId 用户 ID
     * @return 用户评论列表
     */
    public List<UserComment> getUserCommentsByUserId(int userId) {
        return userMapper.getUserCommentsByUserId(userId);
    }

    /**
     * 通过 user_id 获取用户历史记录
     * @param userId 用户 ID
     * @return 用户历史记录列表
     */
    public List<UserHistory> getUserHistoryByUserId(int userId) {
        return userMapper.getUserHistoryByUserId(userId);
    }

    /**
     * 通过 user_id 获取用户互动记录
     * @param userId 用户 ID
     * @return 用户互动记录列表
     */
    public List<UserInteraction> getUserInteractionsByUserId(int userId) {
        return userMapper.getUserInteractionsByUserId(userId);
    }

    /**
     * 通过 user_id 计算用户的点赞、收藏、评论的数量
     * @param userId 用户 ID
     * @return 包含点赞、收藏、评论数量的对象
     */
    public UserInteractionCount countUserInteractions(int userId) {
        return userMapper.countUserInteractions(userId);
    }

    public int updateUserInfo(UserInfo userInfo) {
        return userMapper.updateUserInfo(userInfo);
    }

    public int updatePassword(int userId, String oldPassword, String newPassword) {
        return userMapper.updatePassword(userId, oldPassword, newPassword);
    }

    /**
     * 获取用户收藏记录
     * @param userId 用户 ID
     * @return 用户收藏记录列表
     */
    public List<UserInteraction> getUserCollectionsByUserId(int userId) {
        return userMapper.getUserCollectionsByUserId(userId);
    }

}
