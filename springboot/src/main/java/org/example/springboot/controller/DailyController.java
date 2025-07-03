package org.example.springboot.controller;

import jakarta.annotation.Resource;
import org.example.springboot.common.Result;
import org.example.springboot.entity.DailyLearning;
import org.example.springboot.service.DailyLearningService;
import org.example.springboot.service.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/daily")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class DailyController {

    @Resource
    private DailyLearningService dailyLearningService;

    @Autowired
    private InteractionService interactionService;

    @GetMapping("/selectAll")
    public Result selectAll(){
        List<DailyLearning> dailyLearnings = dailyLearningService.selectAll();
        return Result.success(dailyLearnings);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        DailyLearning dailyLearning = dailyLearningService.selectById(id);
        return Result.success(dailyLearning);
    }

    @GetMapping("/search")
    public Result searchDaily(@RequestParam String keyword) {
        List<DailyLearning> list = dailyLearningService.searchDaily(keyword);
        return Result.success(list);
    }

    @PostMapping("/likeOrCollect")
    public Result likeOrCollect(@RequestParam int articleId, @RequestParam int userId, @RequestParam String actionType) {
        boolean result = interactionService.handleLikeOrCollect("ARTICLE", articleId, userId, actionType);
        if (result) {
            return Result.success("操作成功");
        } else {
            return Result.error(400, "操作失败");
        }
    }


}
