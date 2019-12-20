package com.blog.ljw.firstbolg.service;

import com.blog.ljw.firstbolg.persistence.UserMapper;
import com.blog.ljw.firstbolg.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void userAdd(User user){
        userMapper.insertUser(user);
    }

    public User getByAccountID(String AccountID){
        return userMapper.getUserByAccountID(AccountID);
    }
}
