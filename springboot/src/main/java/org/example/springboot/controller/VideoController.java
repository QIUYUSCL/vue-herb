package org.example.springboot.controller;

import jakarta.annotation.Resource;
import org.example.springboot.common.Result;
import org.example.springboot.entity.DailyLearning;
import org.example.springboot.entity.VideoInfo;
import org.example.springboot.mapper.VideoInfoMapper;
import org.example.springboot.service.VideoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/video")
@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class VideoController {

    @Resource
    private VideoInfoService videoInfoService;


    @GetMapping("/selectAll")
    public Result selectAll() {
        List<VideoInfo> videoInfos = videoInfoService.selectAll();
        return Result.success(videoInfos);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
         VideoInfo videoInfo = videoInfoService.selectById(id);
        return Result.success(videoInfo);
    }

    @GetMapping("/search")
    public Result searchVideos(@RequestParam String keyword) {
        List<VideoInfo> list = videoInfoService.searchVideos(keyword);
        return Result.success(list);
    }

    @PostMapping("/likeOrCollect")
    public Result likeOrCollect(@RequestParam int videoId, @RequestParam int userId, @RequestParam String actionType) {
        boolean result = videoInfoService.handleLikeOrCollect(videoId, userId, actionType);
        if (result) {
            return Result.success("操作成功");
        } else {
            return Result.error(400, "操作失败");
        }
    }

}
