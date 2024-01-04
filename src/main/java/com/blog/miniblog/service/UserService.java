package com.blog.miniblog.service;

import com.blog.miniblog.entity.User;

public interface UserService {
    public User selectUser(String name);
    public User getUserWithPassword(String name);
    public void setLastLogin(String name);
    public void signUp(User user);
}
