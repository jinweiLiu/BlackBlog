package com.blog.ljw.firstbolg.pojo;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String accountId;
    private String name;
    private String token;
    private String bio;
    private String avatarUrl;
    private int role;
}
