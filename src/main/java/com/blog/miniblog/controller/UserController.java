package com.blog.miniblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.blog.miniblog.entity.User;
import com.blog.miniblog.service.UserService;
import com.blog.miniblog.common.result.Result;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/user/{name}")
    public Object selectUser(@PathVariable String name) {
        User user = userService.selectUser(name);
        Assert.notNull(user, "找不到用户");
        return Result.success(user);
    }
}
