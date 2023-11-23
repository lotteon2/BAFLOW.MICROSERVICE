package com.bit.lot.flower.auth.common.interceptor;

import com.bit.lot.flower.auth.common.MutableHttpServletRequest;
import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
public class CommonLogoutInterceptor implements HandlerInterceptor {

  private final TokenHandler tokenHandler;

  private String getUserId(HttpServletRequest request) throws ServletException {
    try {
      MutableHttpServletRequest mutableRequest = (MutableHttpServletRequest) request;
      Enumeration<String> stringEnumeration = mutableRequest.getHeaders("userId");
      return stringEnumeration.nextElement();
    } catch (NoSuchElementException e) {
      throw new ServletException("userId가 header에 존재하지 않습니다.");
    }
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      @Nullable ModelAndView modelAndView) throws Exception {
    String userId = getUserId(request);
    log.info(userId);
    tokenHandler.invalidateToken(userId, response);
  }


}
