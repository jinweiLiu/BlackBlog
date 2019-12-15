package com.blog.ljw.firstbolg.persistence;

import com.blog.ljw.firstbolg.pojo.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper {

    @Select("select * from account where username = #{username}")
    Account getAccountByname(String username);
    @Select("select * from account where username = #{username} and userpass = #{password}")
    Account getAccountBy(@Param("username") String username , @Param("password") String password);
}
