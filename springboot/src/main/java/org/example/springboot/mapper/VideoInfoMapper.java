package org.example.springboot.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.springboot.entity.VideoInfo;

import java.util.List;

public interface VideoInfoMapper {
    List<VideoInfo> selectAll();

    VideoInfo selectById(int id);

    List<VideoInfo> searchVideo(String s);

    int updateLikes(int videoId);

    int updateCollections(int videoId);

    int decreaseLikes(int videoId);

    int decreaseCollections(int videoId);

    int updateViews(int videoId);

    List<VideoInfo> selectByLikes(int limit);

}
