package com.bit.lot.flower.auth.common.util;

import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class RedisRefreshTokenUtil {

    private  RedisTemplate<String, Object> redisTemplate;


    @Autowired
    public RedisRefreshTokenUtil(
        RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void
    saveRefreshToken(String userId, String refreshToken, long expirationTimeInSeconds) {
        String key = getRefreshTokenKey(userId);
        redisTemplate.opsForValue().set(key, refreshToken, expirationTimeInSeconds, TimeUnit.SECONDS);
    }

    public String getRefreshToken(String userId) {
        String key = getRefreshTokenKey(userId);
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteRefreshToken(String userId) {
        String key = getRefreshTokenKey(userId);
        redisTemplate.delete(key);
    }

    private String getRefreshTokenKey(String userId) {
        return "refresh_token:" + userId;
    }
}