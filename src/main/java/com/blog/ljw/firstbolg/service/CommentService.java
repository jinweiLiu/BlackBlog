package com.blog.ljw.firstbolg.service;

import com.blog.ljw.firstbolg.persistence.CommentMapper;
import com.blog.ljw.firstbolg.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public List<Comment> selectCommentsByArticleId(int articleId){
        return commentMapper.selectCommentsByArticleId(articleId);
    }
}
