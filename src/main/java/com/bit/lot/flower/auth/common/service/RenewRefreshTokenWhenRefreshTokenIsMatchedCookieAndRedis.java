package com.bit.lot.flower.auth.common.service;

import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.util.CookieUtil;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.util.RedisRefreshTokenUtil;
import com.bit.lot.flower.auth.common.valueobject.BaseId;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.social.exception.SocialAuthException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RenewRefreshTokenWhenRefreshTokenIsMatchedCookieAndRedis<ID extends BaseId> implements
    RenewRefreshTokenStrategy<ID> {

  private final RedisRefreshTokenUtil redisRefreshTokenUtil;
  private final TokenHandler tokenHandler;
  @Value("${cookie.refresh.token.name}")
  private String refreshCookieName;

  @Override
  public String renew(ID id, Role role, HttpServletRequest request,
      HttpServletResponse response) {
    String refreshTokenAtCookie = CookieUtil.getCookieValue(request, refreshCookieName);
    if (redisRefreshTokenUtil.getRefreshToken(refreshTokenAtCookie) == null) {
      throw new IllegalArgumentException("유효한 접근이 아닙니다. Refresh토큰을 확인해주세요");
    }
    return tokenHandler.createToken(id.getValue().toString(), createClaimsRoleMap(role), response);
  }

  private Map<String, Object> createClaimsRoleMap(Role role) {
    return JwtUtil.addClaims(
        SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME, role);
  }

}
