package com.example.trello.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public String getRefreshToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setRefreshToken(String key, String data) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data, Duration.ofDays(7));
    }

    public boolean hasRefreshToken(String key) {
        return redisTemplate.hasKey(key);
    }

    public void deleteRefreshToken(String key) {
        redisTemplate.delete(key);
    }

}