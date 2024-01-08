package com.blog.miniblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.blog.miniblog.entity.User;

@Mapper
public interface UserMapper {
    public User selectUser(String name);
    public void setLastLogin(String name);
    public void signUp(User user);
    public void setPassword(User user);
    public void setInfo(User user);
    public void setAvatar(User user);
}
