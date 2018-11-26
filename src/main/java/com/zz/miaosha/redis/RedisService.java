package com.zz.miaosha.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;

    public <T> T get(KeyPrefix prefix,String key, Class<T> clazz){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = jedis.get(prefix.getPrefix()+key);
            T t = strToBean(str,clazz);
            return t;
        }finally {
            returnToPoll(jedis);
        }
    }

    public <T> boolean set(KeyPrefix prefix,String key, T value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = beanTostr(value);
            int second = prefix.expiireSeconds();
            if (str==null||str.length()<=0) return false;
            if(second<=0){
                jedis.set(prefix.getPrefix()+key, str);
            }else {
                jedis.setex(prefix.getPrefix()+key, second ,str);
            }
            return true;
        }finally {
            returnToPoll(jedis);
        }
    }

    private <T> String beanTostr(T value) {
        if(value==null){
            return null;
        }
        Class<?> clzz = value.getClass();
        if(clzz == int.class || clzz == Integer.class){
            return ""+value;
        } else if(clzz == String.class){
            return (String) value;
        }else if (clzz == long.class || clzz == Long.class){
            return ""+value;
        }else
        return JSON.toJSONString(value);
    }

    private <T> T strToBean(String str, Class<T> clzz) {
        if(str==null || str.length()<=0 || clzz==null ) return null;
        if(clzz == int.class || clzz == Integer.class){
            return (T) Integer.valueOf(str);
        } else if(clzz == String.class){
            return (T) str;
        }else if (clzz == long.class || clzz == Long.class){
            return (T) Long.valueOf(str);
        }else
            return JSON.toJavaObject(JSON.parseObject(str),clzz);
    }

    private void returnToPoll(Jedis jedis) {
        if (jedis!=null){
            jedis.close();
        }
    }




}
