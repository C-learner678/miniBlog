package com.blog.miniblog.common.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenException extends RuntimeException{
    public TokenException (String message){
        log.error(message);
    }
}
