package com.blog.miniblog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.blog.miniblog.entity.User;
import com.blog.miniblog.mapper.UserMapper;
import com.blog.miniblog.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    public User selectUser(String name, boolean password) {
        User user = this.userMapper.selectUser(name);
        if(!password){
            user.setPassword(null);
        }
        return user;
    }

    public User selectUserWithPassword(String name) {
        return this.selectUser(name, true);
    }

    public User selectUserWithoutPassword(String name) {
        return this.selectUser(name, false);
    }

    public void setLastLogin(String name) {
        this.userMapper.setLastLogin(name);
    }

    public void signUp(User user){
        this.userMapper.signUp(user);
    }

    public void setPassword(User user) {
        this.userMapper.setPassword(user);
    }

    public void setInfo(User user) {
        this.userMapper.setInfo(user);
    }

    public void setAvatar(User user) {
        this.userMapper.setAvatar(user);
    }
}
