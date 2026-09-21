package com.example.studentmanager;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 处理空指针异常
    @ExceptionHandler(NullPointerException.class)
    public Result<String> handleNullPointerException(NullPointerException e) {
        e.printStackTrace();
        return Result.error("空指针异常，请检查数据");
    }

    // 处理参数类型错误
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> handleIllegalArgumentException(IllegalArgumentException e) {
        e.printStackTrace();
        return Result.error("参数错误：" + e.getMessage());
    }

    // 处理 400 参数缺失
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> handleMissingParam(MissingServletRequestParameterException e) {
        return Result.error(400, "缺少参数：" + e.getParameterName());
    }

    // 处理 405 方法不支持
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<String> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        return Result.error(405, "请求方法不支持：" + e.getMethod());
    }

    // 处理其他所有异常（兜底）
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        e.printStackTrace();
        return Result.error("系统异常：" + e.getMessage());
    }
}