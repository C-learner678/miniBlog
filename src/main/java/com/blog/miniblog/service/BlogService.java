package com.blog.miniblog.service;

import com.blog.miniblog.entity.Blog;

public interface BlogService {
    public Blog selectBlog(Long id);
    public void createBlog(Blog blog);
    public void modifyBlog(Blog blog);
}
