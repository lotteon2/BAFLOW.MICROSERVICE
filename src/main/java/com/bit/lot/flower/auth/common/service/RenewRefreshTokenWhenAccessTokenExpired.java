package com.bit.lot.flower.auth.common.service;

import com.bit.lot.flower.auth.common.exception.AuthException;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.util.RedisBlackListTokenUtil;
import com.bit.lot.flower.auth.common.util.RedisRefreshTokenUtil;
import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServlet;
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
      HttpServletResponse response) throws IOException {
    checkTokenIsRegisteredAsBlackList(expiredToken);
    String refreshToken = redisRefreshTokenUtil.getRefreshToken(expiredToken);
    try {
      JwtUtil.isRefreshTokenValid(refreshToken);
      String newAccessToken = JwtUtil.generateAccessTokenWithClaims(String.valueOf(id.getValue()),
          createRoleMap(role));
      redisRefreshTokenUtil.saveRefreshToken(newAccessToken, refreshToken,
          Long.parseLong(refreshTokenLifeTime) + 60L);
      return newAccessToken;
    } catch (ExpiredJwtException e) {
      setResponseWhenRefreshIsExpired(response);
    } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
      throw new AuthException("유효하지 않은 토큰입니다.");
    }
     throw new IOException("Response 응답 실패");
  }

  private void checkTokenIsRegisteredAsBlackList(String accessToken) {
    if (redisBlackListTokenUtil.isTokenBlacklisted(accessToken)) {
      throw new AuthException("로그아웃 처리된 토큰은 사용할 수 없습니다.");
    }

  }

  private Map<String, Object> createRoleMap(Role role) {
    Map<String, Object> roleClaims = new HashMap<>();
    roleClaims.put(SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME, role);
    return roleClaims;
  }

  private void setResponseWhenRefreshIsExpired(HttpServletResponse response)
      throws IOException {

    response.setStatus(401);

    response.setContentType("application/json");

    String jsonResponse = "{\"message\":\"Refresh-Expired\"}";

    response.getWriter().write(jsonResponse);
  }
}
