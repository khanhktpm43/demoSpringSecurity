package com.exampleSecurity.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;
@Service
public class BlackList {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void addToken(String token) {
        redisTemplate.opsForValue().set(token, token, 1, TimeUnit.DAYS); // Thiết lập TTL cho 1 ngày
    }

    public boolean isTokenExists(String token) {
        return redisTemplate.hasKey(token);
    }
}
