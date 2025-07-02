package org.example.springboot.service;

import jakarta.annotation.Resource;
import org.example.springboot.entity.UserInteraction;
import org.example.springboot.entity.VideoInfo;
import org.example.springboot.mapper.UserMapper;
import org.example.springboot.mapper.VideoInfoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoInfoService {
    @Resource
    private VideoInfoMapper videoInfoMapper;

    @Resource
    private UserMapper userMapper;

    public List<VideoInfo> selectAll(){
        return videoInfoMapper.selectAll();
    }

    public VideoInfo selectById(int id){
        return videoInfoMapper.selectById(id);
    }

    public List<VideoInfo> searchVideos(String keyword) {
        return videoInfoMapper.searchVideo("%" + keyword + "%");
    }

    public boolean handleLikeOrCollect(int videoId, int userId, String actionType) {
        try {

            // 检查用户是否已经执行过该操作
            if (userMapper.hasPerformedAction(userId, videoId, actionType)) {
                return false;
            }
            if ("LIKE".equals(actionType)) {
                videoInfoMapper.updateLikes(videoId);
            } else if ("COLLECT".equals(actionType)) {
                videoInfoMapper.updateCollections(videoId);
            }

            UserInteraction interaction = new UserInteraction();
            interaction.setUser_id(userId);
            interaction.setTarget_type("VIDEO");
            interaction.setTarget_id(videoId);
            interaction.setAction_type(actionType);
            userMapper.insertUserInteraction(interaction);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
