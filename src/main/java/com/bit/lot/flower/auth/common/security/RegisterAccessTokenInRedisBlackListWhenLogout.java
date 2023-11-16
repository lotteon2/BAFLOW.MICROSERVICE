package com.bit.lot.flower.auth.common.security;

import com.bit.lot.flower.auth.common.util.ExtractAuthorizationTokenUtil;
import com.bit.lot.flower.auth.common.util.RedisBlackListTokenUtil;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RegisterAccessTokenInRedisBlackListWhenLogout implements
    JwtAccessTokenDeleteStrategy {


  private final RedisBlackListTokenUtil redisBlackListTokenUtil;
  @Override
  public void invalidateAccessToken(HttpServletRequest request) {
    redisBlackListTokenUtil.addTokenToBlacklist(request.getHeader(ExtractAuthorizationTokenUtil.extractToken(request)),
        Long.parseLong(SecurityPolicyStaticValue.REFRESH_EXPIRATION_TIME));
  }
}
