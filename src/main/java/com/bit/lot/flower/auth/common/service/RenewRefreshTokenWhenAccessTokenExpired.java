package com.bit.lot.flower.auth.common.service;

import com.bit.lot.flower.auth.common.exception.AuthException;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.util.RedisBlackListTokenUtil;
import com.bit.lot.flower.auth.common.util.RedisRefreshTokenUtil;
import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class RenewRefreshTokenWhenAccessTokenExpired implements
    RenewRefreshTokenStrategy<AuthId> {

  private final RedisBlackListTokenUtil redisBlackListTokenUtil;
  private final RedisRefreshTokenUtil redisRefreshTokenUtil;

  @Value("${refresh.token.lifetime}")
  private String refreshTokenLifeTime;

  @Override
  public String renew(AuthId id, Role role, String expiredToken, HttpServletRequest request,
      HttpServletResponse response) {
    checkTokenIsRegisteredAsBlackList(expiredToken);
    try {
      String refreshToken = redisRefreshTokenUtil.getRefreshToken(expiredToken.substring(7));
      JwtUtil.isRefreshTokenValid(refreshToken);
      String newAccessToken = JwtUtil.generateAccessTokenWithClaims(String.valueOf(id.getValue()),
          createRoleMap(role));
      redisRefreshTokenUtil.saveRefreshToken(newAccessToken, refreshToken,
          Long.parseLong(refreshTokenLifeTime) + 60L);
      redisBlackListTokenUtil.addTokenToBlacklist(expiredToken,
          Long.parseLong(refreshTokenLifeTime));
      return newAccessToken;
    } catch (ExpiredJwtException | AuthException e ) {
      throw new AuthException("message: Refresh-Expired");
    } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
      throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
    }
  }

  private void checkTokenIsRegisteredAsBlackList(String accessToken) {
    if (redisBlackListTokenUtil.isTokenBlacklisted(accessToken)) {
      throw new AuthException("이미 사용된 토큰은 사용할 수 없습니다.");
    }

  }

  private Map<String, Object> createRoleMap(Role role) {
    Map<String, Object> roleClaims = new HashMap<>();
    roleClaims.put(SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME, role);
    return roleClaims;
  }

}
