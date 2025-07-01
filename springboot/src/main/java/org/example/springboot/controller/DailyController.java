package org.example.springboot.controller;

import jakarta.annotation.Resource;
import org.example.springboot.common.Result;
import org.example.springboot.entity.DailyLearning;
import org.example.springboot.service.DailyLearningService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/daily")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class DailyController {

    @Resource
    private DailyLearningService dailyLearningService;

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

}
