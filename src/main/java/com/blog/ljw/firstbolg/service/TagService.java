package com.blog.ljw.firstbolg.service;

import com.blog.ljw.firstbolg.persistence.ArticleTagMapper;
import com.blog.ljw.firstbolg.persistence.TagMapper;
import com.blog.ljw.firstbolg.pojo.ArticleTag;
import com.blog.ljw.firstbolg.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    public Tag selectByName(String name){
        return tagMapper.selectByName(name);
    }

    public List<Tag> getAllTag(){
        return tagMapper.selectAll();
    }

    public List<Tag> getTagByArticleId(int articleId){
        return articleTagMapper.selectByArticleId(articleId);
    }

    public int addTag(Tag tag){
        return tagMapper.insertTag(tag)>0?tag.getId():0;
    }

    public int addArticleTag(ArticleTag articleTag){
        return articleTagMapper.insertArticleTag(articleTag);
    }

    public void updateCount(int tagId,int count){
        tagMapper.updateCount(tagId,count);
    }
}
