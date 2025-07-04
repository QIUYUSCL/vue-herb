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

    public VideoInfo selectById(int id) {
        VideoInfo videoInfo = videoInfoMapper.selectById(id);
        if (videoInfo != null && videoInfo.getVideo_url() != null) {
            // 打印原始视频 URL，用于调试
            //System.out.println("原始视频 URL: " + videoInfo.getVideo_url());
            // 将反斜杠替换为正斜杠
            String normalizedUrl = videoInfo.getVideo_url().replace("\\", "/");
            // 替换本地路径为可访问的 URL
            String videoUrl = normalizedUrl.replace("D:/视频/Captures/", "/video-resource/");
            videoInfo.setVideo_url(videoUrl);
            // 打印转换后的视频 URL，用于调试
            //System.out.println("转换后的视频 URL: " + videoUrl);
        }
        return videoInfo;
    }

    public List<VideoInfo> searchVideos(String keyword) {
        return videoInfoMapper.searchVideo("%" + keyword + "%");
    }

    public List<VideoInfo> selectByLikes(int limit) {
        return videoInfoMapper.selectByLikes(limit);
    }


}
