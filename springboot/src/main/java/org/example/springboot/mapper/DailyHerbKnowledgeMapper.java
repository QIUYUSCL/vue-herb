package org.example.springboot.mapper;

import org.example.springboot.entity.DailyHerbKnowledge;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DailyHerbKnowledgeMapper {
    List<DailyHerbKnowledge> selectAll();
    DailyHerbKnowledge selectByDate(String date);
}