package com.bit.lot.flower.auth.common.http.interceptor;

import com.bit.lot.flower.auth.common.security.JwtTokenProcessor;
import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.util.ExtractAuthorizationTokenUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
public class CommonLogoutInterceptor implements HandlerInterceptor {

  private final JwtTokenProcessor jwtTokenProcessor;
  private final TokenHandler tokenHandler;

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      @Nullable ModelAndView modelAndView)  {
    String token = ExtractAuthorizationTokenUtil.extractToken(request);
    String id = jwtTokenProcessor.extractUserId(request);
    tokenHandler.invalidateToken(id, token,response);
  }

}
