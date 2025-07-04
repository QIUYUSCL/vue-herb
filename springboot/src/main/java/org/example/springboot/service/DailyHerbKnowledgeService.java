package org.example.springboot.service;

import org.example.springboot.entity.DailyHerbKnowledge;
import org.example.springboot.mapper.DailyHerbKnowledgeMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;

@Service
public class DailyHerbKnowledgeService {
    @Resource
    private DailyHerbKnowledgeMapper dailyHerbKnowledgeMapper;

    public List<DailyHerbKnowledge> selectAll() {
        return dailyHerbKnowledgeMapper.selectAll();
    }

    public DailyHerbKnowledge selectByDate(String date) {
        return dailyHerbKnowledgeMapper.selectByDate(date);
    }
}