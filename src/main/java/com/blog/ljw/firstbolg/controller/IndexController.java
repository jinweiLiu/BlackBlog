package com.blog.ljw.firstbolg.controller;

import com.blog.ljw.firstbolg.dto.ArticleDto;
import com.blog.ljw.firstbolg.pojo.Article;
import com.blog.ljw.firstbolg.pojo.Category;
import com.blog.ljw.firstbolg.pojo.Tag;
import com.blog.ljw.firstbolg.service.ArticleService;
import com.blog.ljw.firstbolg.service.RedisService;
import com.blog.ljw.firstbolg.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String Index(Model model){
        //分页还需完善
        List<Article> articleList = articleService.selectLatestArticles(0,4);
        //包含article、click和tag
        List<ArticleDto> articleDtos = new ArrayList<>();
        for(Article article:articleList){
            ArticleDto articleDto = new ArticleDto();
            articleDto.setArticle(article);
            String clicks = redisService.get(String.valueOf(article.getId()));
            if(clicks == null){
                articleDto.setClickCount("0");
            }else{
                articleDto.setClickCount(clicks);
            }
            List<Tag> tags= tagService.getTagByArticleId(article.getId());
            articleDto.setTag(tags);
            articleDtos.add(articleDto);
        }
        model.addAttribute("articleDtos",articleDtos);

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
