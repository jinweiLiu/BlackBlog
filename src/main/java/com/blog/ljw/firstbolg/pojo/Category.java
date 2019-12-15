package com.blog.ljw.firstbolg.pojo;

import java.util.HashMap;
import java.util.Map;

public class Category {
    private Map<String,Object> objects = new HashMap();

    public void set(String key, Object value){
        objects.put(key,value);
    }

    public Object get(String key){
        return objects.get(key);
    }
}
