package com.blog.ljw.firstbolg.pojo;

import lombok.Data;

@Data
public class Account {
    private int accountid;
    private String username;
    private String userpass;

    @Override
    public String toString(){
        return accountid + username + userpass;
    }
}
