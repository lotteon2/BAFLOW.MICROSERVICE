package com.bit.lot.flower.auth.common.security;

import com.bit.lot.flower.auth.common.util.CookieUtil;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.util.RedisBlackListTokenUtil;
import com.bit.lot.flower.auth.common.util.RedisRefreshTokenUtil;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import java.time.Duration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class IssueRefreshRefreshTokenInCookie implements
    RefreshTokenStrategy {

  @Value("${cookie.http.domain")
  private  String domain;
  @Value("cookie.refresh.token.name")
  private  String refreshCookieName;


  private final RedisRefreshTokenUtil redisRefreshTokenUtil;

  @Override
  public void createRefreshToken(HttpServletRequest request) {
    String userId = (String) request.getAttribute("userId");
    String refreshToken = JwtUtil.generateRefreshToken(String.valueOf(userId));
    redisRefreshTokenUtil.saveRefreshToken(userId, refreshToken,
        Long.parseLong(SecurityPolicyStaticValue.REFRESH_EXPIRATION_TIME));
    CookieUtil.createHttpOnlyCookie(refreshCookieName + userId, refreshToken,
        Duration.ofDays(1), domain);
  }

  @Override
  public void invalidateRefreshToken(HttpServletRequest request, HttpServletResponse response) {
    CookieUtil.deleteCookie(refreshCookieName, domain);
    redisRefreshTokenUtil.deleteRefreshToken(request.getHeader("userId"));
  }
}
