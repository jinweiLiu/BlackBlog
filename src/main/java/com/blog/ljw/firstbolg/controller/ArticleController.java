package com.blog.ljw.firstbolg.controller;

import com.blog.ljw.firstbolg.pojo.Article;
import com.blog.ljw.firstbolg.pojo.Comment;
import com.blog.ljw.firstbolg.service.ArticleService;
import com.blog.ljw.firstbolg.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/article/id")
    public String ShowArticle(@RequestParam("articleId") String articleId , Model model){
        Article article = articleService.getArticlById(articleId);
        model.addAttribute("article",article);
        List<Comment> commentList = commentService.selectCommentsByArticleId(Integer.parseInt(articleId));
        model.addAttribute("commentList",commentList);
        return "article";
    }

    @PostMapping("/article/articleAdd")
    public String AddArticle(@ModelAttribute("article") Article article,@ModelAttribute("tag") String tag){
        Date date = new Date();
        //SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        article.setCreatedDate(date);
        //dateFormat.format(date);
        articleService.insertArticle(article);
        return "create";
    }
}
