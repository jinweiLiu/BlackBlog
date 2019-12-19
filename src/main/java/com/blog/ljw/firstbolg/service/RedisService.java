package com.blog.ljw.firstbolg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ArticleService articleService;

    private final String HOT = "hotArticle";

    public String get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean delete(final String key) {
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean increment(final String key){
        boolean result = false;
        try {
            redisTemplate.opsForValue().increment(key);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean getAndset(final String key,int index){
        boolean result = false;
        try {
            String old = redisTemplate.opsForValue().get(key);
            int change = Integer.parseInt(old.split(":")[index])+1;
            if(index==0){
                redisTemplate.opsForValue().set(key,change+":"+old.split(":")[1]);
            }else{
                redisTemplate.opsForValue().set(key,old.split(":")[0]+":"+change);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean zsetadd(final String key,final String score){
        boolean result = false;
        try {
            redisTemplate.opsForZSet().add(HOT, key, Double.parseDouble(score));
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Set<String> zrevenge(int start, int end){
        Set<String>  set = redisTemplate.opsForZSet().reverseRange(HOT,start,end);
        return set;
    }

    /*public List<Article> getHotArticle(){
        List<Article> Hots = new ArrayList<>();
        Set<String> set =  zrevenge(0,6);
        for(String hot : set){
            Hots.add(articleService.getArticlById(hot));
        }
        return Hots;
    }*/

}
