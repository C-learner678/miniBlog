package com.blog.miniblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.blog.miniblog.entity.Blog;

@Mapper
public interface BlogMapper {
    public Blog selectBlog(Long id);
    public void insertBlog(Blog blog);
    public void updateBlog(Blog blog);
}
