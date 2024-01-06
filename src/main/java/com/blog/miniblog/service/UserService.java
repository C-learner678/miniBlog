package com.blog.miniblog.service;

import com.blog.miniblog.entity.User;

public interface UserService {
    public User selectUser(String name, boolean returnPassword);
    public User selectUserWithPassword(String name);
    public User selectUserWithoutPassword(String name);
    public void setLastLogin(String name);
    public void signUp(User user);
    public void setPassword(User user);
}
