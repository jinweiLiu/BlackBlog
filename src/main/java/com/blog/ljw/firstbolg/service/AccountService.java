package com.blog.ljw.firstbolg.service;

import com.blog.ljw.firstbolg.persistence.AccountMapper;
import com.blog.ljw.firstbolg.pojo.Account;
import com.blog.ljw.firstbolg.pojo.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountMapper accountMapper;


    public  Account getAccountByname(String username){
        return accountMapper.getAccountByname(username);
    }
    public  Account getAccountBy(String username ,String password){
        return accountMapper.getAccountBy(username,password);
    }
}
