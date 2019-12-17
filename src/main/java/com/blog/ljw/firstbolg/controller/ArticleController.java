package com.blog.ljw.firstbolg.controller;

import com.blog.ljw.firstbolg.pojo.Article;
import com.blog.ljw.firstbolg.pojo.ArticleTag;
import com.blog.ljw.firstbolg.pojo.Comment;
import com.blog.ljw.firstbolg.pojo.Tag;
import com.blog.ljw.firstbolg.service.ArticleService;
import com.blog.ljw.firstbolg.service.CommentService;
import com.blog.ljw.firstbolg.service.RedisService;
import com.blog.ljw.firstbolg.service.TagService;
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

    @Autowired
    private RedisService redisService;

    @Autowired
    private TagService tagService;

    @GetMapping("/article/id")
    public String ShowArticle(@RequestParam("articleId") String articleId , Model model){
        String clicks = redisService.get(articleId);
        if(clicks==null){
            redisService.set(articleId,"1");
        }else{
            redisService.increment(articleId);
        }
        String clickCount = redisService.get(articleId);
        model.addAttribute("clickCount",clickCount);
        Article article = articleService.getArticlById(articleId);
        model.addAttribute("article",article);
        List<Tag> tags= tagService.getTagByArticleId(article.getId());
        model.addAttribute("tags",tags);
        List<Comment> commentList = commentService.selectCommentsByArticleId(Integer.parseInt(articleId));
        model.addAttribute("commentList",commentList);
        return "article";
    }

    @PostMapping("/article/articleAdd")
    public String AddArticle(@ModelAttribute("article") Article article,@ModelAttribute("tag") String tag){
        Date date = new Date();
        article.setCreatedDate(date);
        articleService.insertArticle(article);

        String []tags = tag.split("，");
        for(String tagName : tags){
            if(tagService.selectByName(tagName)==null){
                Tag newTag = new Tag();
                newTag.setCount(1);
                newTag.setName(tagName);
                int newTagId = tagService.addTag(newTag);

                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(newTagId);
                tagService.addArticleTag(articleTag);
            }else{
                tagService.updateCount(tagService.selectByName(tagName).getId(),tagService.selectByName(tagName).getCount()+1);
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(tagService.selectByName(tagName).getId());
                articleTag.setArticleId(article.getId());
                tagService.addArticleTag(articleTag);
            }
        }
        return "create";
    }
}
