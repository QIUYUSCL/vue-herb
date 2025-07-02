package org.example.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.springboot.entity.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    List<UserInfo> selectAll();

    UserInfo selectById(Integer user_id);

    UserInfo selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    int insertUser(UserInfo userInfo);

    /**
     * 通过 user_id 获取用户评论
     * @param userId 用户 ID
     * @return 用户评论列表
     */
    List<UserComment> getUserCommentsByUserId(@Param("userId") int userId);

    /**
     * 通过 user_id 获取用户历史记录
     * @param userId 用户 ID
     * @return 用户历史记录列表
     */
    List<UserHistory> getUserHistoryByUserId(@Param("userId") int userId);

    /**
     * 通过 user_id 获取用户互动记录
     * @param userId 用户 ID
     * @return 用户互动记录列表
     */
    List<UserInteraction> getUserInteractionsByUserId(@Param("userId") int userId);


    /**
     * 通过 user_id 计算用户的点赞、收藏、评论的数量
     * @param userId 用户 ID
     * @return 包含点赞、收藏、评论数量的对象
     */
    UserInteractionCount countUserInteractions(@Param("userId") int userId);

    /**
     * 更新用户信息
     * @param userInfo 用户信息实体
     * @return 更新成功的记录数
     */
    int updateUserInfo(UserInfo userInfo);


    int updatePassword(@Param("userId") int userId, @Param("oldPassword") String oldPassword, @Param("newPassword") String newPassword);

    /**
     * 插入用户互动记录
     * @param interaction 用户互动对象
     * @return 插入成功的记录数
     */
    /**
     * 检查用户是否已经对指定目标执行过指定操作
     * @param userId 用户 ID
     * @param targetId 目标 ID
     * @param actionType 操作类型（LIKE 或 COLLECT）
     * @return 若已执行过该操作返回 true，否则返回 false
     */
    @Select("SELECT COUNT(*) > 0 FROM user_interaction WHERE user_id = #{userId} AND target_id = #{targetId} AND action_type = #{actionType}")
    boolean hasPerformedAction(@Param("userId") int userId, @Param("targetId") int targetId, @Param("actionType") String actionType);
    int insertUserInteraction(UserInteraction interaction);

    /**
     * 获取用户收藏记录
     * @param userId 用户 ID
     * @return 用户收藏记录列表
     */
    List<UserInteraction> getUserCollectionsByUserId(@Param("userId") int userId);

    int hasViewedToday(@Param("params") Map<String, Object> params);
    int insertUserHistory(UserHistory userHistory);

}
