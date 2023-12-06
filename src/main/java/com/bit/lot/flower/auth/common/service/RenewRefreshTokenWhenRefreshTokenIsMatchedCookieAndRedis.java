package com.bit.lot.flower.auth.common.service;

import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.util.CookieUtil;
import com.bit.lot.flower.auth.common.util.RedisRefreshTokenUtil;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RenewRefreshTokenWhenRefreshTokenIsMatchedCookieAndRedis implements
    RenewRefreshTokenStrategy {

  private final TokenHandler tokenHandler;
  @Value("${cookie.refresh.token.name}")
  private String refreshCookieName;
  private RedisRefreshTokenUtil redisRefreshTokenUtil;

  @Override
  public String renew(HttpServletRequest request) {
    String refreshTokenAtCookie = CookieUtil.getCookieValue(request, refreshCookieName);
    redisRefreshTokenUtil.getRefreshToken()
  }

}
