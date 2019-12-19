package com.blog.ljw.firstbolg.controller;

import com.blog.ljw.firstbolg.pojo.*;
import com.blog.ljw.firstbolg.service.ArticleService;
import com.blog.ljw.firstbolg.service.CommentService;
import com.blog.ljw.firstbolg.service.RedisService;
import com.blog.ljw.firstbolg.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public String ShowArticle(@RequestParam("articleId") String articleId , Model model,
                              HttpServletResponse response, HttpServletRequest request){
        String clicks = redisService.get(articleId);
        if(clicks==null){
            redisService.set(articleId,"1:0");
        }else{
            redisService.getAndset(articleId,0);
        }
        String clickCount = redisService.get(articleId).split(":")[0];
        model.addAttribute("clickCount",clickCount);  //设置点击数

        redisService.zsetadd(articleId,clickCount);  //根据点赞数选取最热文章

        Article article = articleService.getArticlById(articleId);
        model.addAttribute("article",article);  //设置文章对象
        List<Tag> tags= tagService.getTagByArticleId(article.getId());
        model.addAttribute("tags",tags);  //设置tag列表
        List<Comment> commentList = commentService.selectCommentsByArticleId(Integer.parseInt(articleId));
        model.addAttribute("commentList",commentList);  //设置评论列表
        String likeCount = "赞 "+redisService.get(articleId).split(":")[1];
        model.addAttribute("likeCount",likeCount);  //设置点赞数

        boolean flag = true;  //有问题
        Cookie[]cookies = request.getCookies();
        for(Cookie c:cookies){
            if(c.getName() == "articleLike") flag = false;
        }
        System.out.println(flag);
        if(flag){
            Cookie cookie = new Cookie("articleLike",null);
            cookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cookie);
        }

        return "article";
    }

    @PostMapping("/article/articleAdd")
    public ModelAndView AddArticle(@ModelAttribute("article") Article article, @RequestParam(name="tag") String tag){
        Date date = new Date();
        article.setCreatedDate(date);
        int articleId = articleService.insertArticle(article);
        redisService.zsetadd(String.valueOf(articleId),"0");

        String []tags = tag.split("，");
        for(String tagName : tags){
            if(tagService.selectByName(tagName)==null){
                Tag newTag = new Tag();
                newTag.setCount(1);
                newTag.setName(tagName);
                int newTagId = tagService.addTag(newTag);

                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(newTagId);
                tagService.addArticleTag(articleTag);
            }else{
                tagService.updateCount(tagService.selectByName(tagName).getId(),tagService.selectByName(tagName).getCount()+1);
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(tagService.selectByName(tagName).getId());
                articleTag.setArticleId(articleId);
                tagService.addArticleTag(articleTag);
            }
        }
        ModelAndView  model = new ModelAndView("/create");
        model.addObject("message", "保存成功");
        return model;
    }

    @GetMapping("/create")
    public String create(Model model, HttpSession session){
        Account account = (Account) session.getAttribute("account");
        if(account!=null){
            Article article = new Article();
            model.addAttribute("article",article);
            String tag = "";
            model.addAttribute("tag",tag);
            return "create";
        }else {
            return "redirect:/";
        }
    }

    @PostMapping("/article/like")
    @ResponseBody
    public String getLikeCount(String articleId){
        int likeCount = Integer.parseInt(redisService.get(articleId).split(":")[1])+1;
        redisService.getAndset(articleId,1);
        String articleCache = String.valueOf(likeCount);
        return articleCache;
    }

}
