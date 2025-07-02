package org.example.springboot.controller;

import jakarta.annotation.Resource;
import org.example.springboot.common.Result;
import org.example.springboot.entity.HerbCategory;
import org.example.springboot.entity.HerbInfo;
import org.example.springboot.service.HerbCategoryService;
import org.example.springboot.service.HerbInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/herb")
@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class HerbController {

    @Resource
    private HerbInfoService herbInfoService;

    @Resource
    private HerbCategoryService herbCategoryService;

    @GetMapping("/info/selectAll")
    public Result selectAllHerbInfo() {
        List<HerbInfo> list = herbInfoService.selectAll();
        return Result.success(list);
    }

    @GetMapping("/info/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        HerbInfo herbInfo = herbInfoService.selectById(id);
        return Result.success(herbInfo);
    }

    @GetMapping("/category/selectAll")
    public Result selectAllHerbCategory() {
        List<HerbCategory> list = herbCategoryService.selectAll();
        return Result.success(list);
    }

    @GetMapping("/info/search")
    public Result searchHerbs(@RequestParam String keyword) {
        List<HerbInfo> list = herbInfoService.searchHerbs(keyword);
        return Result.success(list);
    }

    @PostMapping("/likeOrCollect")
    public Result likeOrCollect(@RequestParam int herbId, @RequestParam int userId, @RequestParam String actionType) {
        boolean result = herbInfoService.handleLikeOrCollect(herbId, userId, actionType);
        if (result) {
            return Result.success("操作成功");
        } else {
            return Result.error(400, "操作失败");
        }
    }

}


