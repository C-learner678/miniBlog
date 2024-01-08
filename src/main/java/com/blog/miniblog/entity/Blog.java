package com.blog.miniblog.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Blog {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private String content;
    private Integer status;
    private Date created;
    private Date lastModified;
}
