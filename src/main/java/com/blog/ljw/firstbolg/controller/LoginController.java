package com.blog.ljw.firstbolg.controller;

import com.blog.ljw.firstbolg.pojo.Account;
import com.blog.ljw.firstbolg.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/login")
    public String AdminLogin(@RequestParam("username") String username,@RequestParam("password") String password,HttpSession session){
        String str = "";
        Account account = accountService.getAccountBy(username,password);
        if(account !=null){
            str = "index";
            session.setAttribute("account",account);
        }else{
            str = "login";
        }
        return str;
    }
}
