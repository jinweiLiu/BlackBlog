package com.blog.ljw.firstbolg.service;

import com.blog.ljw.firstbolg.dto.ArticleDto;
import com.blog.ljw.firstbolg.dto.PaginationDto;
import com.blog.ljw.firstbolg.persistence.ArticleMapper;
import com.blog.ljw.firstbolg.persistence.ArticleTagMapper;
import com.blog.ljw.firstbolg.pojo.Article;
import com.blog.ljw.firstbolg.pojo.Tag;
import com.blog.ljw.firstbolg.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class PaginationService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private TagService tagService;

    public PaginationDto getPages(int page, int size){
        PaginationDto paginationDto = new PaginationDto();

        int totalArticle = articleMapper.getArticleCount();
        int totalpage;
        if(totalArticle/size == 0){
            totalpage = totalArticle / size;
        }else{
            totalpage = totalArticle / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalpage) {
            page = totalpage;
        }
        paginationDto.setPagination(totalpage,page);
        int offset = page < 1 ? 0 : size * (page - 1);
        List<Article> articleList = articleMapper.selectLatestArticles(offset,size);
        //包含article、click和tag
        List<ArticleDto> articleDtos = new ArrayList<>();
        List<Tag> paginationTags = new ArrayList<>();
        for(Article article:articleList){
            ArticleDto articleDto = new ArticleDto();
            articleDto.setArticle(article);
            String clicks = redisUtil.get(String.valueOf(article.getId())).split(":")[0];
            if(clicks == null){
                articleDto.setClickCount("0");
            }else{
                articleDto.setClickCount(clicks);
            }
            List<Tag> tags= articleTagMapper.selectByArticleId(article.getId());
            paginationTags.addAll(tags);
            articleDto.setTag(tags);
            articleDtos.add(articleDto);
        }
        paginationDto.setData(articleDtos);
        paginationTags = new ArrayList<Tag>(new LinkedHashSet<>(paginationTags));
        paginationDto.setTags(paginationTags);
        return paginationDto;
    }
}
