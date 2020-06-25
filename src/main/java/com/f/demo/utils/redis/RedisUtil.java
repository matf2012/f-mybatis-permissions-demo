package com.f.demo.utils.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 * Created by matf on 2019-10-12.
 */
@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final long TIME = 1L;
    public final String REDIS_CACHE_PREV = "QXB_CARD_";


    public String get(String key){
        return stringRedisTemplate.opsForValue().get(getKey(key));
    }

    public void set(String key,String value){
//        stringRedisTemplate.expire(key,TIME, TimeUnit.HOURS);
        stringRedisTemplate.opsForValue().set(getKey(key),value,TIME, TimeUnit.HOURS);
    }
    public void set(String key,String value,long expire){
//        stringRedisTemplate.expire(key,expire, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(getKey(key),value,expire, TimeUnit.SECONDS);
    }

    private String getKey(String key){
        return REDIS_CACHE_PREV+key;
    }

    public void set(String key,Object value){
        String json = JSON.toJSONString(value);
        this.set(getKey(key),json);
    }

    public String getValue(String type,String code){
        String value = null;
        String jsonMap = this.get(type);
        if(jsonMap != null){
            JSONObject jo = JSON.parseObject(jsonMap);
            value = jo.getString(code);
        }
        return value;
    }
    /**
     * 获取缓存MAP的对象
     * 如缓存  ABC : {'A':OBject1,'B':Object2}
     * TYPE = 'ABC' , CODE = 'a' => OBject1
     * @param type 缓存KEY
     * @param code MAP的KEY
     * @param c value 的CLASS
     * @param <T> 泛型
     * @return
     */
    public <T> T getObject(String type,String code,Class<T> c){
        String value = null;
        T t = null;
        String jsonMap = this.get(type);
        if(jsonMap != null){
            JSONObject jo = JSON.parseObject(jsonMap);
            value = jo.getString(code);
            if(StringUtils.isNotBlank(value)){
                t = JSON.parseObject(value,c);
            }
        }
        return t;
    }

}
