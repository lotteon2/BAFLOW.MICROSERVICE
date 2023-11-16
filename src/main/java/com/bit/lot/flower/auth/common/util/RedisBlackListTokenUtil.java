package com.bit.lot.flower.auth.common.util;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedisBlackListTokenUtil {

  private static final String BLACKLIST_KEY = "logout-blacklist:";

 private final RedisTemplate<String, String> redisTemplate;

  public  void addTokenToBlacklist(String token, long expirationTimeSeconds) {
    String key = BLACKLIST_KEY + token;
    redisTemplate.opsForValue().set(key, "blacklisted", expirationTimeSeconds, TimeUnit.SECONDS);
  }

  public boolean isTokenBlacklisted(String token) {
    String key = BLACKLIST_KEY + token;
    return redisTemplate.hasKey(key);
  }
}
