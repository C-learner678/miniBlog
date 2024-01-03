package com.blog.miniblog.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.blog.miniblog.common.security.RSA;
import com.blog.miniblog.common.result.Result;

@RestController
public class RSAController {
    @GetMapping("/getPublicKey")
    public Result getPublicKey() {
        return Result.success(RSA.getPublicKey());
    }
}