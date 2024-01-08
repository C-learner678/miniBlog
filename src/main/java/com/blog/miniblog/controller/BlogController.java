package com.blog.miniblog.controller;

import java.util.Map;

import com.blog.miniblog.common.security.RSA;
import com.blog.miniblog.common.security.RSAUtils;
import com.blog.miniblog.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import com.blog.miniblog.entity.Blog;
import com.blog.miniblog.service.BlogService;
import com.blog.miniblog.common.result.Result;
import com.blog.miniblog.common.security.JWTUtils;

@Slf4j
@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;
    @PostMapping("/createBlog")
    public Object login(@RequestBody Blog blog){
        User user = JWTUtils.getCurrentUser();
        Assert.notNull(user, "用户未登录");
        blog.setUserId(user.getId());
        blogService.createBlog(blog);
        return Result.success();
    }
}
