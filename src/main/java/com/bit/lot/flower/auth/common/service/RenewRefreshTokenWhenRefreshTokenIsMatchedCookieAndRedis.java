package com.bit.lot.flower.auth.common.service;

import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.util.CookieUtil;
import com.bit.lot.flower.auth.common.util.RedisRefreshTokenUtil;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.social.exception.SocialAuthException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
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
    if(redisRefreshTokenUtil.getRefreshToken(refreshTokenAtCookie)==null){
      throw new IllegalArgumentException("유효한 접근이 아닙니다. Refresh토큰을 확인해주세요");
    }
    tokenHandler.createToken();
  }

}
