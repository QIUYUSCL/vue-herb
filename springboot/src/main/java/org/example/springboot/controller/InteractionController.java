package org.example.springboot.controller;

import jakarta.annotation.Resource;
import org.example.springboot.common.Result;
import org.example.springboot.entity.UserComment;
import org.example.springboot.mapper.UserMapper;
import org.example.springboot.service.InteractionService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/interaction")
public class InteractionController {

    @Resource
    private InteractionService interactionService;

    @Resource
    private UserMapper userMapper;

    @GetMapping("/view")
    public Result handleView(@RequestParam String targetType, @RequestParam int targetId, @RequestParam int userId) {
        interactionService.handleView(targetType, targetId, userId);
        return Result.success("浏览记录处理成功");
    }

    @PostMapping("/likeOrCollect")
    public Result handleLikeOrCollect(@RequestParam String targetType, @RequestParam int targetId, @RequestParam int userId, @RequestParam String actionType) {
        // 补充 targetType 参数
        boolean hasPerformed = userMapper.hasPerformedAction(userId, targetId, actionType, targetType);
        boolean result = interactionService.handleLikeOrCollect(targetType, targetId, userId, actionType);
        if (result) {
            return Result.success(hasPerformed ? "操作已取消" : "操作成功", Map.of("hasPerformed", hasPerformed));
        } else {
            return Result.error();
        }
    }

    @PostMapping("/addComment")
    public Result addComment(@RequestBody UserComment userComment) {
        try {
            int insertResult = userMapper.insertUserComment(userComment);
            if (insertResult > 0) {
                int updateResult = userMapper.updateCommentsCount(userComment.getTarget_type(), userComment.getTarget_id());
                if (updateResult > 0) {
                    return Result.success("评论发表成功");
                }
            }
            return Result.error("500","评论发表失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("400","评论发表失败，服务器错误");
        }
    }

}