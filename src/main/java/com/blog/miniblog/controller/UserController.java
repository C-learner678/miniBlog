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
import com.blog.miniblog.common.security.JWTUtils;

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
    public Object login(@RequestBody User user){
        String name = user.getName();
        String password = user.getPassword();
        User u2 = userService.getUserWithPassword(name);
        Assert.notNull(u2, "用户名或密码错误");
        //使用RSA私钥解密前端用公钥加密过的密码
        password = RSAUtils.decode(password, RSA.getPrivateKey());
        //再使用BCrypt重新加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //与数据库中已加密过的密码对比
        Assert.isTrue(passwordEncoder.matches(password, u2.getPassword()), "用户名或密码错误");
        userService.setLastLogin(name);
        return Result.success(JWTUtils.getToken(name, u2.getPassword()));
    }
    @PostMapping("/signUp")
    public Object signUp(@RequestBody User user){
        String name = user.getName();
        String password = user.getPassword();
        //先验证是否已经存在同名用户
        User u2 = userService.selectUser(name);
        Assert.isNull(u2, "已存在该用户");
        //使用RSA私钥解密前端用公钥加密过的密码
        password = RSAUtils.decode(password, RSA.getPrivateKey());
        //再使用BCrypt重新加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        userService.signUp(user);
        return Result.success("注册成功");
    }
}
