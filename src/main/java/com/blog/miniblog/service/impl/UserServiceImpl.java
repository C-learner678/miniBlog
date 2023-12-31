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

    public User selectUser(String name) {
        return this.userMapper.selectUser(name);
    }
}
