package org.example.springboot.service;

import jakarta.annotation.Resource;
import org.example.springboot.entity.UserHistory;
import org.example.springboot.entity.UserInteraction;
import org.example.springboot.mapper.DailyLearningMapper;
import org.example.springboot.mapper.HerbInfoMapper;
import org.example.springboot.mapper.UserMapper;
import org.example.springboot.mapper.VideoInfoMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InteractionService {

    @Resource
    private HerbInfoMapper herbInfoMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private VideoInfoMapper videoInfoMapper;

    @Resource
    private DailyLearningMapper dailyLearningMapper;

    /**
     * 处理浏览操作
     * @param targetType 目标类型，如 'HERB', 'VIDEO', 'ARTICLE'
     * @param targetId 目标 ID
     * @param userId 用户 ID
     */
    public void handleView(String targetType, int targetId, int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("targetId", targetId);
        params.put("targetType", targetType);

        int count = userMapper.hasViewedToday(params);
        if (count == 0) {
            switch (targetType) {
                case "HERB":
                    herbInfoMapper.updateViews(targetId);
                    break;
                case "VIDEO":
                    // 这里需要添加视频浏览量更新的 mapper 方法调用
                    videoInfoMapper.updateViews(targetId);
                    break;
                case "ARTICLE":
                    // 这里需要添加文章浏览量更新的 mapper 方法调用
                    dailyLearningMapper.updateViews(targetId);
                    break;
            }
            UserHistory userHistory = new UserHistory();
            userHistory.setUser_id(userId);
            userHistory.setTarget_type(targetType);
            userHistory.setTarget_id(targetId);
            userMapper.insertUserHistory(userHistory);
        }
    }

    /**
     * 处理点赞或收藏操作
     * @param targetType 目标类型，如 'HERB', 'VIDEO', 'ARTICLE'
     * @param targetId 目标 ID
     * @param userId 用户 ID
     * @param actionType 操作类型，'LIKE' 或 'COLLECT'
     * @return 操作结果，成功返回 true，失败返回 false
     */
    public boolean handleLikeOrCollect(String targetType, int targetId, int userId, String actionType) {
        try {

            boolean hasPerformed = userMapper.hasPerformedAction(userId, targetId, actionType);
            if (hasPerformed) {
                // 取消操作
                userMapper.deleteUserInteraction(userId, targetId, actionType);
                switch (targetType) {
                    case "HERB":
                        if ("LIKE".equals(actionType)) {
                            herbInfoMapper.decreaseLikes(targetId);
                        } else if ("COLLECT".equals(actionType)) {
                            herbInfoMapper.decreaseCollections(targetId);
                        }
                        break;
                    case "VIDEO":
                        if ("LIKE".equals(actionType)) {
                            videoInfoMapper.decreaseLikes(targetId);
                        } else if ("COLLECT".equals(actionType)) {
                            videoInfoMapper.decreaseCollections(targetId);
                        }
                        break;
                    case "ARTICLE":
                        if ("LIKE".equals(actionType)) {
                            dailyLearningMapper.decreaseLikes(targetId);
                        } else if ("COLLECT".equals(actionType)) {
                            dailyLearningMapper.decreaseCollections(targetId);
                        }
                        break;
                }
            } else {
                // 执行操作
                switch (targetType) {
                    case "HERB":
                        if ("LIKE".equals(actionType)) {
                            herbInfoMapper.updateLikes(targetId);
                        } else if ("COLLECT".equals(actionType)) {
                            herbInfoMapper.updateCollections(targetId);
                        }
                        break;
                    case "VIDEO":
                        if ("LIKE".equals(actionType)) {
                            videoInfoMapper.updateLikes(targetId);
                        } else if ("COLLECT".equals(actionType)) {
                            videoInfoMapper.updateCollections(targetId);
                        }
                        break;
                    case "ARTICLE":
                        if ("LIKE".equals(actionType)) {
                            dailyLearningMapper.updateLikes(targetId);
                        } else if ("COLLECT".equals(actionType)) {
                            dailyLearningMapper.updateCollections(targetId);
                        }
                        break;
                }
                UserInteraction interaction = new UserInteraction();
                interaction.setUser_id(userId);
                interaction.setTarget_type(targetType);
                interaction.setTarget_id(targetId);
                interaction.setAction_type(actionType);
                userMapper.insertUserInteraction(interaction);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}