package org.example.springboot.mapper;

import org.example.springboot.entity.DailyLearning;

import java.util.List;

public interface DailyLearningMapper {
    List<DailyLearning> selectAll();

    DailyLearning selectById(Integer id);

    List<DailyLearning> searchDaily(String keyword);

    int updateLikes(int articleId);

    int updateCollections(int articleId);

    int updateViews(int articleId);
}
