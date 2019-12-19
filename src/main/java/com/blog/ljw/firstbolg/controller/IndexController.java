package com.blog.ljw.firstbolg.controller;

import com.blog.ljw.firstbolg.dto.PaginationDto;
import com.blog.ljw.firstbolg.pojo.Article;
import com.blog.ljw.firstbolg.pojo.Category;
import com.blog.ljw.firstbolg.service.ArticleService;
import com.blog.ljw.firstbolg.service.PaginationService;
import com.blog.ljw.firstbolg.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class IndexController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private PaginationService paginationService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/")
    public String Index(@RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "4") Integer size,
                        Model model){
        PaginationDto pagination = paginationService.getPages(page,size);
        model.addAttribute("pagination",pagination);

        Category category = articleService.getCategory();
        model.addAttribute("category",category);

        List<Article> hotArticles = new ArrayList<>();
        Set<String> set =  redisService.zrevenge(0,6);
        for(String hot : set){
            hotArticles.add(articleService.getArticlById(hot));
        }
        model.addAttribute("hotArticles",hotArticles);
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

}
