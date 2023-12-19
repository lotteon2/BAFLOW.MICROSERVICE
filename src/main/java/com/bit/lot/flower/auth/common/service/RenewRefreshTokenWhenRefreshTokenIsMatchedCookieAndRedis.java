package com.bit.lot.flower.auth.common.service;

import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.util.CookieUtil;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.util.RedisBlackListTokenUtil;
import com.bit.lot.flower.auth.common.util.RedisRefreshTokenUtil;
import com.bit.lot.flower.auth.common.valueobject.BaseId;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RenewRefreshTokenWhenRefreshTokenIsMatchedCookieAndRedis<ID extends BaseId> implements
    RenewRefreshTokenStrategy<ID> {

  private final RedisBlackListTokenUtil redisBlackListTokenUtil;
  private final RedisRefreshTokenUtil redisRefreshTokenUtil;
  private final TokenHandler tokenHandler;
  @Value("${cookie.refresh.token.name}")
  private String refreshCookieName;

  @Override
  public String renew(ID id, Role role, String expiredAccessToken, HttpServletRequest request,
      HttpServletResponse response) {

    checkTokenIsBlacklist(expiredAccessToken);


    try {
      JwtUtil.isRefreshTokenValid(id.toString());
    } catch (ExpiredJwtException e) {
      throw new JwtException("refresh 토큰이 만료되었습니다. 다시 로그인 해주세요.");
    }

    checkRefreshTokenIsExistedBothRedisAndCookie(id,request);

    return createNewTokenWithInvalidatingTheOldToken(id, role, expiredAccessToken, response);
  }

  private void checkTokenIsBlacklist(String accessToken) {
    if (redisBlackListTokenUtil.isTokenBlacklisted(accessToken)) {
      throw new IllegalArgumentException("이미 블랙리스트에 등록된  토큰은 사용할 수 없습니다.");
    }
  }

  private void checkRefreshTokenIsExistedBothRedisAndCookie(ID id, HttpServletRequest request) {
    String refreshCookieValue = CookieUtil.getCookieValue(request, refreshCookieName);

    if (!redisRefreshTokenUtil.getRefreshToken(id.getValue().toString())
        .equals(refreshCookieValue)) {
      throw new IllegalArgumentException("유효한 접근이 아닙니다. Refresh토큰을 확인해주세요");
    }
  }

  private String createNewTokenWithInvalidatingTheOldToken(ID id, Role role,
      String expiredAccessToken,
      HttpServletResponse response) {
    tokenHandler.invalidateToken(id.getValue().toString(), expiredAccessToken, response);
    return tokenHandler.createToken(id.getValue().toString(), createClaimsRoleMap(role), response);
  }


  private Map<String, Object> createClaimsRoleMap(Role role) {
    return JwtUtil.addClaims(
        SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME, role);
  }

}
