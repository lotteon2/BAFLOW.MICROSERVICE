package com.bit.lot.flower.auth.social.http.interceptor;

import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.social.http.valueobject.UserId;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class SocialAuthLoginInterceptor implements HandlerInterceptor {

  private final TokenHandler tokenHandler;

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      @Nullable ModelAndView modelAndView) throws Exception {
    UserId userId = (UserId) request.getAttribute("userId");
    response.addHeader(SecurityPolicyStaticValue.TOKEN_AUTHORIZAION_HEADER_NAME,
        createJwtToken(request, userId));
  }

  private String createJwtToken(HttpServletRequest request, UserId userId) {
    Map<String, Object> claimList = new HashMap<>();
    claimList.put(SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME,
        String.valueOf(userId.getValue()));
    return tokenHandler.createToken(request, claimList);
  }
}
