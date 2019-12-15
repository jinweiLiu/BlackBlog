package com.blog.ljw.firstbolg.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private int id;
    private int userId;
    private int articleId;
    private String content;
    private Date createdDate;
    private int status;
}
