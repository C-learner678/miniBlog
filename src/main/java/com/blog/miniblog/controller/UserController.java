package com.blog.miniblog.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import com.blog.miniblog.entity.User;
import com.blog.miniblog.service.UserService;
import com.blog.miniblog.common.result.Result;
import com.blog.miniblog.common.security.RSA;
import com.blog.miniblog.common.security.RSAUtils;

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
    @PostMapping("/login")
    public Object getPassword(@RequestBody User user, HttpSession session){
        String name = user.getName();
        String password = user.getPassword();
        User u2 = userService.getUserWithPassword(name);
        Assert.notNull(u2, "找不到该用户");
        password = RSAUtils.decode(password, RSA.getPrivateKey());
        Assert.isTrue(password.equals(u2.getPassword()), "密码错误");
        session.setAttribute("user_name", name);
        return Result.success("登录成功");
    }
}
