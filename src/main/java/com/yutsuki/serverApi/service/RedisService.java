package com.yutsuki.serverApi.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, 1, java.util.concurrent.TimeUnit.HOURS);
    }

    public void set(String key, String value, long expireTime, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, expireTime, timeUnit);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean isExpiredKey(String key) {
        Long ttl = redisTemplate.getExpire(key);
        return (ttl != null && ttl == -2);
    }

    public Boolean deleteKey(String key) {
        return redisTemplate.delete(key);
    }
}
