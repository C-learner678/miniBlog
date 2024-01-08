package com.blog.miniblog.service;

import com.blog.miniblog.entity.User;

public interface UserService {
    public User selectUser(Long id, boolean returnPassword);
    public User selectUserByName(String name, boolean returnPassword);
    public void signUp(User user);
    public void setLastLogin(Long id);
    public void setPassword(User user);
    public void setInfo(User user);
    public void setAvatar(User user);
}
