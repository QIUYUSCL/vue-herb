package org.example.springboot.mapper;

import org.example.springboot.entity.VideoInfo;

import java.util.List;

public interface VideoInfoMapper {
    List<VideoInfo> selectAll();

    VideoInfo selectById(int id);

    List<VideoInfo> searchVideo(String s);
}
