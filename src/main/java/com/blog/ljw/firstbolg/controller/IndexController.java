package com.blog.ljw.firstbolg.controller;

import com.blog.ljw.firstbolg.pojo.Article;
import com.blog.ljw.firstbolg.pojo.Category;
import com.blog.ljw.firstbolg.pojo.Tag;
import com.blog.ljw.firstbolg.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/")
    public String Index(Model model){
        List<Article> articleList = articleService.getAll();
        model.addAttribute("articleList",articleList);
        String []categoryName = {"JAVA","Web","Linux","Network","Database","Algorithm","Other"};
        Category category = new Category();
        for(String name:categoryName){
            category.set(name,articleService.getCategoryCount(name));
        }
        model.addAttribute("category",category);
        return "index";
    }

    @GetMapping("/admin")
    public String Login(){
        return "login";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }

    @GetMapping("/category")
    public String category(){
        return "category";
    }

    @GetMapping("/create")
    public String create(Model model){
        Article article = new Article();
        model.addAttribute("article",article);
        String tag = "";
        model.addAttribute("tag",tag);
        return "create";
    }
}
