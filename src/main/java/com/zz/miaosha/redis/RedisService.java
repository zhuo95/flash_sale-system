package com.zz.miaosha.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 判断key是否存在
     * */
    public <T> boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.exists(realKey);
        }finally {
            returnToPoll(jedis);
        }
    }

    /**
     * 删除
     * */
    public boolean delete(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            long ret =  jedis.del(realKey);
            return ret > 0;
        }finally {
            returnToPoll(jedis);
        }
    }


    /**
     * 增加值
     * */
    public <T> Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.incr(realKey);
        }finally {
            returnToPoll(jedis);
        }
    }

    /**
     * 减少值
     * */
    public <T> Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.decr(realKey);
        }finally {
            returnToPoll(jedis);
        }
    }

    //删掉所有
    public boolean delete(KeyPrefix prefix) {
        if(prefix == null) {
            return false;
        }
        List<String> keys = scanKeys(prefix.getPrefix());
        if(keys==null || keys.size() <= 0) {
            return true;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(keys.toArray(new String[0]));
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    public List<String> scanKeys(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> keys = new ArrayList<String>();
            String cursor = "0";
            ScanParams sp = new ScanParams();
            sp.match("*"+key+"*");
            sp.count(100);
            do{
                ScanResult<String> ret = jedis.scan(cursor, sp);
                List<String> result = ret.getResult();
                if(result!=null && result.size() > 0){
                    keys.addAll(result);
                }
                //再处理cursor
                cursor = ret.getStringCursor();
            }while(!cursor.equals("0"));
            return keys;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    public  <T> String beanTostr(T value) {
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

    public  <T> T strToBean(String str, Class<T> clzz) {
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
