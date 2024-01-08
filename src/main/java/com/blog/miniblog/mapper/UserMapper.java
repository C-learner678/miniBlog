package com.blog.miniblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.blog.miniblog.entity.User;

@Mapper
public interface UserMapper {
    public User selectUser(Long id);
    public User selectUserByName(String name);
    public void insertUser(User user);
    public void setLastLogin(Long id);
    public void setPassword(User user);
    public void setInfo(User user);
    public void setAvatar(User user);
}
