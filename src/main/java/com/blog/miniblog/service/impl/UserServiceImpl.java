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

    public User selectUser(Long id, boolean returnPassword) {
        User user = this.userMapper.selectUser(id);
        if(!returnPassword && user != null){
            user.setPassword(null);
        }
        return user;
    }

    public User selectUserByName(String name, boolean returnPassword) {
        User user = this.userMapper.selectUserByName(name);
        if(!returnPassword && user != null){
            user.setPassword(null);
        }
        return user;
    }

    public void signUp(User user){
        this.userMapper.insertUser(user);
    }

    public void setLastLogin(Long id) {
        this.userMapper.setLastLogin(id);
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
