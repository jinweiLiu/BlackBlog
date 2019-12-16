package com.blog.ljw.firstbolg.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Article {
    private int id;
    private String title;
    private String describes;
    private String content;
    private Date createdDate;
    private int commentCount;
    private String category;

    @Override
    public String toString(){
        return id+title+describes;
        //System.out.println(id+title+describes);
    }
}
