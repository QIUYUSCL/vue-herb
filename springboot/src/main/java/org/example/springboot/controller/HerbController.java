package org.example.springboot.controller;

import jakarta.annotation.Resource;
import org.example.springboot.common.Result;
import org.example.springboot.entity.HerbCategory;
import org.example.springboot.entity.HerbInfo;
import org.example.springboot.service.HerbCategoryService;
import org.example.springboot.service.HerbInfoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/herb")
@RestController
@CrossOrigin(origins = "*")
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
//    public List<HerbInfo> selectAll(){
//        return herbInfoService.selectAll();
//    }

    @GetMapping("/category/selectAll")
    public Result selectAllHerbCategory() {
        List<HerbCategory> list = herbCategoryService.selectAll();
        return Result.success(list);
    }

//    public List<HerbCategory> selectAllCategory(){
//        return herbCategoryService.selectAll();
//    }
}
