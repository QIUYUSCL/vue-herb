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

    public List<DailyLearning> selectByLikes(int limit) {
        return dailyLearningMapper.selectByLikes(limit);
    }

}
