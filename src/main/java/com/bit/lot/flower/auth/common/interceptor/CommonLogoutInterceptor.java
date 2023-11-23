package com.bit.lot.flower.auth.common.interceptor;

import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Component
public class CommonLogoutInterceptor implements HandlerInterceptor {


  private final TokenHandler tokenHandler;
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      @Nullable ModelAndView modelAndView) throws Exception {
    tokenHandler.invalidateToken(request.getHeader(SecurityPolicyStaticValue.USER_ID_HEADER_NAME),response);
}
}
