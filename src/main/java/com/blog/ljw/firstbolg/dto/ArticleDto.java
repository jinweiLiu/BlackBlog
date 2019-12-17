package com.blog.ljw.firstbolg.dto;

import com.blog.ljw.firstbolg.pojo.Article;
import com.blog.ljw.firstbolg.pojo.Tag;
import lombok.Data;

import java.util.List;

@Data
public class ArticleDto {
    private Article article;
    private String clickCount;
    private List<Tag> tag;
}
