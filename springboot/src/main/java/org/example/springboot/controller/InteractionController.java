package org.example.springboot.controller;

import jakarta.annotation.Resource;
import org.example.springboot.common.Result;
import org.example.springboot.service.InteractionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interaction")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class InteractionController {

    @Resource
    private InteractionService interactionService;

    @GetMapping("/view")
    public Result handleView(@RequestParam String targetType, @RequestParam int targetId, @RequestParam int userId) {
        interactionService.handleView(targetType, targetId, userId);
        return Result.success("浏览记录处理成功");
    }

    @PostMapping("/likeOrCollect")
    public Result handleLikeOrCollect(@RequestParam String targetType, @RequestParam int targetId, @RequestParam int userId, @RequestParam String actionType) {
        boolean result = interactionService.handleLikeOrCollect(targetType, targetId, userId, actionType);
        if (result) {
            return Result.success("操作成功");
        } else {
            return Result.error();
        }
    }
}