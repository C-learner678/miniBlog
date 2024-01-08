package com.blog.miniblog.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.miniblog.common.result.Result;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler{
    //Token
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = TokenException.class)
    public Result handler(TokenException e){
        log.error("Token异常：" + e.getMessage());
        return Result.result(401, e.getMessage(), "请重新登录");
    }
    //Assert
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e){
        log.error("Assert异常：" + e.getMessage());
        return Result.fail(e.getMessage());
    }
    //其他
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e) {
        log.error("运行时异常：" + e.getMessage());
        return Result.fail(e.getMessage());
    }
}
