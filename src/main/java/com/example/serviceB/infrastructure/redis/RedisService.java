package com.example.serviceB.infrastructure.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    public boolean setIfAbsent(String key, String value, long ttlInSeconds) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value, ttlInSeconds, TimeUnit.SECONDS);
        return result != null && result;
    }
}
