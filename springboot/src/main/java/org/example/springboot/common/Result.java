package org.example.springboot.common;


// 统一返回结果类
//统一后端返回的数据类型
public class Result {
    private String code;
    private String msg;
    private Object data;

    public Result() {
    }


    public static Result error() {
        Result result = new Result();
        result.setCode("500");
        result.setMsg("系统错误");
        return result;
    }

    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result success() {
        Result result = new Result();
        result.setCode("200");
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode("200");
        result.setData(data);
        return result;
    }

    public static Result error(int code, String msg) {
        return error(String.valueOf(code), msg);
    }

    public static Result success(String msg, Object data) {
        Result result = new Result();
        result.setCode("200");
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}


