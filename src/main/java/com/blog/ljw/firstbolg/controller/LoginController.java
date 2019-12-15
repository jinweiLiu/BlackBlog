package com.blog.ljw.firstbolg.controller;

import com.blog.ljw.firstbolg.pojo.Account;
import com.blog.ljw.firstbolg.pojo.Article;
import com.blog.ljw.firstbolg.pojo.Category;
import com.blog.ljw.firstbolg.service.AccountService;
import com.blog.ljw.firstbolg.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private AccountService accountService;

    @RequestMapping("/login")
    public String AdminLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Model model){
        String str = "";
        Account account = accountService.getAccountBy(username,password);
        if(account !=null){
            List<Article> articleList = articleService.getAll();
            model.addAttribute("articleList",articleList);
            String []categoryName = {"JAVA","Web","Linux","Network","Database","Algorithm","Other"};
            Category category = new Category();
            for(String name:categoryName){
                category.set(name,articleService.getCategoryCount(name));
            }
            model.addAttribute("category",category);
            str = "index";
            session.setAttribute("account",account);
        }else{
            str = "login";
        }
        return str;
    }
}
