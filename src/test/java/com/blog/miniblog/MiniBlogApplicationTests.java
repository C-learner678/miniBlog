package com.blog.miniblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blog.miniblog.entity.User;
import com.blog.miniblog.mapper.UserMapper;

@SpringBootTest
class MiniBlogApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        User user = userMapper.selectUser("C-learner678");
        System.out.println(user);
    }

}
