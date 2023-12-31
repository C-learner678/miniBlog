package com.blog.miniblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.blog.miniblog.entity.User;

@Mapper
public interface UserMapper {
    public User selectUser(String name);
}
