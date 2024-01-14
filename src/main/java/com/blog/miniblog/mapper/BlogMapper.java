package com.blog.miniblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.blog.miniblog.entity.Blog;

@Mapper
public interface BlogMapper {
    public Blog selectBlog(Long id);
    public void insertBlog(Blog blog);
    public void updateBlog(Blog blog);
    public Blog selectUnfinishedBlogByUser(Long userId);
    public void insertUnfinishedBlog(Blog blog);
    public void updateUnfinishedBlogByUser(Blog blog);
    public void deleteUnfinishedBlogByUser(Long userId);
}
