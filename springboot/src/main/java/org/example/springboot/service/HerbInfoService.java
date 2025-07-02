package org.example.springboot.service;

import jakarta.annotation.Resource;
import org.example.springboot.entity.HerbInfo;
import org.example.springboot.entity.UserInteraction;
import org.example.springboot.mapper.HerbInfoMapper;
import org.example.springboot.mapper.UserMapper;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class HerbInfoService {

    @Resource
    private HerbInfoMapper herbInfoMapper;

    @Resource
    private UserMapper userMapper;

    public List<HerbInfo> selectAll(){
        return herbInfoMapper.selectAll();
    }

    public HerbInfo selectById(Integer id) {
        return herbInfoMapper.selectById(id);
    }

    public List<HerbInfo> searchHerbs(String keyword) {
        return herbInfoMapper.searchHerbs("%" + keyword + "%");
    }
    /**
     * 处理点赞或收藏操作
     * @param herbId 药材 ID
     * @param userId 用户 ID
     * @param actionType 操作类型（LIKE 或 COLLECT）
     * @return 操作结果，成功返回 true，失败返回 false
     */
    public boolean handleLikeOrCollect(int herbId, int userId, String actionType) {
        try {
            // 检查用户是否已经执行过该操作
            if (userMapper.hasPerformedAction(userId, herbId, actionType)) {
                return false;
            }

            if ("LIKE".equals(actionType)) {
                herbInfoMapper.updateLikes(herbId);
            } else if ("COLLECT".equals(actionType)) {
                herbInfoMapper.updateCollections(herbId);
            } else {
                return false;
            }

            UserInteraction interaction = new UserInteraction();
            interaction.setUser_id(userId);
            interaction.setTarget_type("HERB");
            interaction.setTarget_id(herbId);
            interaction.setAction_type(actionType);
            userMapper.insertUserInteraction(interaction);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
