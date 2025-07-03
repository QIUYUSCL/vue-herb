package org.example.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.entity.DailyLearning;

import java.util.List;

@Mapper
public interface DailyLearningMapper {
    List<DailyLearning> selectAll();

    DailyLearning selectById(Integer id);

    List<DailyLearning> searchDaily(String keyword);

    int updateLikes(int articleId);

    int updateCollections(int articleId);

    int updateViews(int articleId);

    int decreaseLikes(int articleId);

    int decreaseCollections(int articleId);

    List<DailyLearning> selectByLikes(int limit);

}
