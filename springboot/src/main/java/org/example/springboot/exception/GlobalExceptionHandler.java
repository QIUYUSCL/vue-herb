package org.example.springboot.exception;

import org.example.springboot.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.example.springboot.common.Result;

@ControllerAdvice("org.example.springboot.controller")
//全局异常处理
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody //返回JSON数据
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error();
    }
    @ExceptionHandler(CustomException.class)
    @ResponseBody //返回JSON数据
    public Result error(CustomException e){
        return Result.error(e.getCode(),e.getMsg());
    }
}
