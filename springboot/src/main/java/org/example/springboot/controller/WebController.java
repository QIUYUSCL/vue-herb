package org.example.springboot.controller;


import org.example.springboot.common.Result;
import org.example.springboot.exception.CustomException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//测试
@RestController
public class WebController {

    @GetMapping("/hello")   //映射 HTTP GET 请求
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/weather")
    public String weather() {
        return "Weather is cloud!";
    }

    @GetMapping("/count")
    public Result count(){
        int i = 1/0;
        return Result.success(10);
    }

    @GetMapping("/test")
    public Result test(){
        //手动抛出错误
        throw new CustomException("400","错误");
        //throw new RuntimeException("错误");
    }
}
