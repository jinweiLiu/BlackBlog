package com.blog.ljw.firstbolg.persistence;

import com.blog.ljw.firstbolg.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    String TABLE_NAEM = " comment ";
    String INSERT_FIELDS = " article_id, content, created_date, user_id, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAEM,"where article_id = #{articleId} " +
            "order by created_date desc"})
    List<Comment> selectCommentsByArticleId(@Param("articleId") int articleId);
}
