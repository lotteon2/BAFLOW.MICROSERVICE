package com.bit.lot.flower.auth.common.util;

import com.bit.lot.flower.auth.common.exception.AuthException;
import java.util.concurrent.TimeUnit;
import lombok.NoArgsConstructor;
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

    public void saveRefreshToken(String key, String refreshToken, long expirationTimeInSeconds) {
        redisTemplate.opsForValue()
            .set(key, refreshToken, expirationTimeInSeconds, TimeUnit.SECONDS);
    }

    public String getRefreshToken(String key) {
        String value = (String) redisTemplate.opsForValue().get(key);
        if(value == null){
            throw new AuthException("refresh 토큰이 만료되었습니다.");
        }
        return value;
    }

    public void deleteRefreshToken(String key) {
        redisTemplate.delete(key);
    }

}