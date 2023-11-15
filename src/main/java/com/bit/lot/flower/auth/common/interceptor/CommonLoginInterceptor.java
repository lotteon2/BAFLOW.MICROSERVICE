package com.bit.lot.flower.auth.common.interceptor;

import com.bit.lot.flower.auth.common.util.CookieUtil;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.util.RedisRefreshTokenUtil;
import java.time.Duration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Configuration
public class CommonLoginInterceptor implements HandlerInterceptor {

  @Value("${cookie.http.domain")
  private final String domain;
  @Value("cookie.refresh.token.name")
  private final String refreshCookieName;
  @Value("jwt.refresh.lifetime")
  private final String redisRefreshTokenLifeTime;

  private final RedisRefreshTokenUtil redisRefreshTokenUtil;

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      @Nullable ModelAndView modelAndView) throws Exception {
    String userId = (String) request.getAttribute("userId");
    String accessToken = JwtUtil.generateAccessToken(String.valueOf(userId));
    String refreshToken = JwtUtil.generateRefreshToken(String.valueOf(userId));
    response.addHeader("Authorization", "Bearer " + accessToken);
    redisRefreshTokenUtil.saveRefreshToken(userId, refreshToken,
        Long.parseLong(redisRefreshTokenLifeTime));
    CookieUtil.createHttpOnlyCookie(refreshCookieName + userId, refreshToken,
        Duration.ofDays(1), domain);
  }
}
