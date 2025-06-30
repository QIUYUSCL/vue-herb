package org.example.springboot.controller;

import jakarta.annotation.Resource;
import org.example.springboot.common.Result;
import org.example.springboot.entity.User;
import org.example.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;



    @GetMapping("/selectAll")
    public Result selectAll()
    {
        List<User> list=userService.selectAll();
        return Result.success(list);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id)
    {
        User user=userService.selectById(id);
        return Result.success(user);
    }

    @GetMapping("/selectOne")
    public Result selectOne(@RequestParam Integer id,String name)
    {
        User user=userService.selectById(id);
        return Result.success(user);
    }

}
