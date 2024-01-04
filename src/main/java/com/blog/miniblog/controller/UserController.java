package com.blog.miniblog.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import com.blog.miniblog.entity.User;
import com.blog.miniblog.service.UserService;
import com.blog.miniblog.common.result.Result;
import com.blog.miniblog.common.security.RSA;
import com.blog.miniblog.common.security.RSAUtils;

@Slf4j
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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Assert.isTrue(passwordEncoder.matches(password, u2.getPassword()), "密码错误");
        userService.setLastLogin(name);
        session.setAttribute("user_name", name);
        return Result.success("登录成功");
    }
    @PostMapping("/signUp")
    public Object signUp(@RequestBody User user, HttpSession session){
        String password = user.getPassword();
        password = RSAUtils.decode(password, RSA.getPrivateKey());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        userService.signUp(user);
        session.setAttribute("user_name", user.getName());
        return Result.success("注册成功");
    }
}
