package com.blog.ljw.firstbolg.persistence;

import com.blog.ljw.firstbolg.pojo.Article;
import com.blog.ljw.firstbolg.pojo.ArticleTag;
import com.blog.ljw.firstbolg.pojo.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleTagMapper {
    String TABLE_NAEM = " article_tag ";
    String INSERT_FIELDS = " article_id, tag_id ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    String TAG_FIELDS = " id, name, count ";
    String ARTICLE_FIELDS = " id, title, describes, content, created_Date as createdDate, comment_Count as commentCount, category ";

    @Insert({"insert into",TABLE_NAEM,"(",INSERT_FIELDS,") values (#{articleId},#{tagId})"})
    int insertArticleTag(ArticleTag articleTag);

    @Select({"select",TAG_FIELDS,"from tag where id in (select tag_id from article_tag where article_id=#{articleId})"})
    List<Tag> selectByArticleId(int articleId);

    @Select({"select",ARTICLE_FIELDS,"from article where id in (select article_id from article_tag where tag_id=#{tagId}) limit #{offset},#{limit}"})
    List<Article> selectByTagId(@Param("tagId") int tagId, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from article where id in (select article_id from article_tag where tag_id=#{tagId})"})
    int selectArticleCountByTagId(@Param("tagId") int tagId);

    @Delete({"delete from",TABLE_NAEM,"where id=#{id}"})
    void deleteById(int id);
}
