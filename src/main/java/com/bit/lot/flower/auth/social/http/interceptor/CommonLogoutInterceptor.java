package com.bit.lot.flower.auth.social.http.interceptor;

import com.bit.lot.flower.auth.common.util.CookieUtil;
import com.bit.lot.flower.auth.common.util.RedisBlackListTokenUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Component
public class CommonLogoutInterceptor implements HandlerInterceptor {

  private final RedisBlackListTokenUtil redisBlackListTokenUtil;
  @Value("jwt.access.lifetime")
  private final long TOKEN_EXPIRATION_TIME;
  @Value("cookie.refresh.token.name")
  private final String refreshName;
  @Value("${cookie.http.domain")
  private final String domain;


  @Override
  public void postHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    String accessToken = (String) request.getAttribute("Authorization");
    redisBlackListTokenUtil.addTokenToBlacklist(accessToken, TOKEN_EXPIRATION_TIME);
    CookieUtil.deleteCookie(refreshName,domain);

  }


}
