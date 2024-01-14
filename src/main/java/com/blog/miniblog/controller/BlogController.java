package com.blog.miniblog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.blog.miniblog.common.security.RSA;
import com.blog.miniblog.common.security.RSAUtils;
import com.blog.miniblog.entity.User;
import com.blog.miniblog.service.UserService;
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
    private UserService userService;
    @Autowired
    private BlogService blogService;
    @GetMapping("/blog/{blogId}")
    public Object getBlog(@PathVariable String blogId) {
        Long id = Long.valueOf(blogId);
        Blog blog = blogService.selectBlog(id);
        User author = userService.selectUser(blog.getUserId(), false);
        author.setPassword(null);
        List<Object> r = new ArrayList<>();
        r.add(blog);
        r.add(author);
        return Result.success(r);
    }
    @PostMapping("/createBlog")
    public Object createBlog(@RequestBody Blog blog){
        User user = JWTUtils.getCurrentUser();
        Assert.notNull(user, "用户未登录");
        blog.setUserId(user.getId());
        blogService.createBlog(blog);
        //创建完成后，删掉之前保存的
        if(blogService.selectUnfinishedBlogByUser(user.getId()) != null) {
            blogService.deleteUnfinishedBlogByUser(user.getId());
        }
        return Result.success();
    }
    @PostMapping("/saveBlog")
    public Object saveBlog(@RequestBody Blog blog){
        User user = JWTUtils.getCurrentUser();
        Assert.notNull(user, "用户未登录");
        blog.setUserId(user.getId());
        if(blogService.selectUnfinishedBlogByUser(user.getId()) == null) {
            blogService.saveBlog(blog);
        }else{
            blogService.updateUnfinishedBlogByUser(blog);
        }
        return Result.success();
    }
    @PostMapping("/getUnfinishedBlog")
    public Object getUnfinishedBlog(){
        User user = JWTUtils.getCurrentUser();
        Assert.notNull(user, "用户未登录");
        Blog unfinishedBlog = blogService.selectUnfinishedBlogByUser(user.getId());
        if(unfinishedBlog == null){
            return Result.success();
        }else{
            return Result.success(unfinishedBlog);
        }
    }
}
