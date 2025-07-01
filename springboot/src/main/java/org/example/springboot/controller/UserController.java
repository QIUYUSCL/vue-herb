package org.example.springboot.controller;

import jakarta.annotation.Resource;
import org.example.springboot.common.Result;
import org.example.springboot.entity.UserInfo;
import org.example.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    @Resource
    private UserService userService;



    @GetMapping("/selectAll")
    public Result selectAll()
    {
        List<UserInfo> list=userService.selectAll();
        return Result.success(list);
    }

    @GetMapping("/selectById/{user_id}")
    public Result selectById(@PathVariable Integer user_id)
    {
        UserInfo user = userService.selectById(user_id);
        if (user != null) {
            return Result.success("获取用户信息成功", user);
        } else {
            return Result.error(404, "未找到该用户信息");
        }
    }



    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        if (username == null || password == null) {
            return Result.error(400, "用户名和密码不能为空");
        }
        UserInfo user = userService.login(username, password);
        if (user != null) {
            return Result.success("登录成功", user);
        } else {
            return Result.error(401, "用户名或密码错误");
        }
    }

}
