package org.example.springboot.service;

import jakarta.annotation.Resource;
import org.example.springboot.entity.VideoInfo;
import org.example.springboot.mapper.VideoInfoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoInfoService {
    @Resource
    private VideoInfoMapper videoInfoMapper;

    public List<VideoInfo> selectAll(){
        return videoInfoMapper.selectAll();
    }

    public VideoInfo selectById(int id){
        return videoInfoMapper.selectById(id);
    }

    public List<VideoInfo> searchVideos(String keyword) {
        return videoInfoMapper.searchVideo("%" + keyword + "%");
    }
}
