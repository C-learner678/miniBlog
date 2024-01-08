package com.blog.miniblog.entity;

import java.util.Date;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String password;
    private String email;
    private Integer status;
    private Date created;
    private Date lastLogin;
    private String description;
    private String avatar;
}
