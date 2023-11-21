package com.bit.lot.flower.auth.common.util;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class RedisRefreshTokenUtil {


    private final RedisTemplate<Object, Object> redisTemplate;

    public void saveRefreshToken(String userId, String refreshToken, long expirationTimeInSeconds) {
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