package com.blog.ljw.firstbolg.persistence;

import com.blog.ljw.firstbolg.pojo.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {

    String TABLE_NAEM = " article ";
    String INSERT_FIELDS = " title, describes, content, created_Date, comment_Count, category ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Select("select id,title, describes, content, created_Date as createdDate, comment_Count as commentCount, category from article")
    List<Article> getAll();

    @Select("select id,title, describes, content, created_Date as createdDate, comment_Count as commentCount, category from article order by id desc limit #{offset},#{limit}")
    List<Article> selectLatestArticles(@Param("offset") int offset, @Param("limit") int limit);

    @Select("select count(id) from article where category = #{category}")
    int getCategoryCount(@Param("category")String category);

    @Select("select id, title, describes, content, created_Date as createdDate, comment_Count as commentCount, category from article where id = #{articleId}")
    Article getArticlById(@Param("articleId") String articleId);

    @Insert({"insert into",TABLE_NAEM,"(",INSERT_FIELDS,") values (#{title},#{describes},#{content}" +
            ",#{createdDate},#{commentCount},#{category})"})
    int insertArticle(Article article);
}
