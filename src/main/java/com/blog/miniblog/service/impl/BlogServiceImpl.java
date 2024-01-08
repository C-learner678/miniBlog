package com.blog.miniblog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.blog.miniblog.entity.Blog;
import com.blog.miniblog.mapper.BlogMapper;
import com.blog.miniblog.service.BlogService;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    public Blog selectBlog(Long id) {
        return this.blogMapper.selectBlog(id);
    }

    public void createBlog(Blog blog) {
        this.blogMapper.insertBlog(blog);
    }

    public void modifyBlog(Blog blog) {
        this.blogMapper.updateBlog(blog);
    }
}
