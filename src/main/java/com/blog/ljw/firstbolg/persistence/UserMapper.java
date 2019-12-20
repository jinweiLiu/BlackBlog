package com.blog.ljw.firstbolg.persistence;

import com.blog.ljw.firstbolg.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    String InsertField = "(account_id,name,token,avatar_url)";
    String SelectField = "id,account_id as accountId,name,token,bio,avatar_url as avatarUrl,role";
    @Insert("insert into user"+InsertField+"values (#{accountId},#{name},#{token},#{avatarUrl})")
    int insertUser(User user);

    @Select("select "+SelectField+" from user where account_id = #{AccountID}")
    User getUserByAccountID(String AccountID);
}
