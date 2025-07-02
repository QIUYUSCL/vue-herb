package org.example.springboot.service;

import jakarta.annotation.Resource;
import org.example.springboot.entity.DailyLearning;
import org.example.springboot.entity.UserInteraction;
import org.example.springboot.mapper.DailyLearningMapper;
import org.example.springboot.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyLearningService {

    @Resource
    private DailyLearningMapper dailyLearningMapper;

    @Resource
    private UserMapper userMapper;

    public List<DailyLearning> selectAll(){
        return dailyLearningMapper.selectAll();
    }

    public DailyLearning selectById(Integer id) {
        return dailyLearningMapper.selectById(id);
    }

    public List<DailyLearning> searchDaily(String keyword) {
        return dailyLearningMapper.searchDaily("%" + keyword + "%");
    }

    public boolean handleLikeOrCollect(int articleId, int userId, String actionType) {
        try {
            // 检查用户是否已经执行过该操作
            if (userMapper.hasPerformedAction(userId, articleId, actionType)) {
                return false;
            }

            if ("LIKE".equals(actionType)) {
                dailyLearningMapper.updateLikes(articleId);
            } else if ("COLLECT".equals(actionType)) {
                dailyLearningMapper.updateCollections(articleId);
            }

            UserInteraction interaction = new UserInteraction();
            interaction.setUser_id(userId);
            interaction.setTarget_type("ARTICLE");
            interaction.setTarget_id(articleId);
            interaction.setAction_type(actionType);
            userMapper.insertUserInteraction(interaction);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
