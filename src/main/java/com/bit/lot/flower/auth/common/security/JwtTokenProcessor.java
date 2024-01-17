package com.bit.lot.flower.auth.common.security;

import com.bit.lot.flower.auth.common.util.ExtractAuthorizationTokenUtil;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import io.jsonwebtoken.Claims;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class JwtTokenProcessor {

  public static String accessKey;
  public static String refreshKey;

  public String extractUserId(HttpServletRequest request) {

    return JwtUtil.extractTokenSubject(ExtractAuthorizationTokenUtil.extractToken(request),
        accessKey);
  }

  public  String extractRole(HttpServletRequest request) {
    String token = ExtractAuthorizationTokenUtil.extractToken(request);
    Claims claims = JwtUtil.extractTokenClaims(token,accessKey);
    return (String) claims.get(SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME);
  }

  @Value("${encrypt.key.access}")
  private void setAccessKey(String accessKey) {
    JwtTokenProcessor.accessKey = accessKey;
  }

  @Value("${encrypt.key.refresh}")
  private void setRefreshKey(String refreshKey) {
    JwtTokenProcessor.refreshKey = refreshKey;
  }

  public String createRefreshToken(String subject) {
    return JwtUtil.generateToken(subject, refreshKey,
        Long.parseLong(SecurityPolicyStaticValue.REFRESH_EXPIRATION_TIME));
  }

  public void validateRefreshToken(String token) {
    JwtUtil.isTokenValid(token, refreshKey);
  }

  public void validateAccessToken(String token) {
    JwtUtil.isTokenValid(token, accessKey);
  }

  private String createAccessTokenWithoutClaims(String subject) {
    return JwtUtil.generateToken(subject, accessKey,
        Long.parseLong(SecurityPolicyStaticValue.ACCESS_EXPIRATION_TIME));
  }

  private String createAccessTokenWithClaims(String subject, Map<String, Object> claimsList) {
    return JwtUtil.generateTokenWithClaims(subject, accessKey,
        Long.parseLong(SecurityPolicyStaticValue.ACCESS_EXPIRATION_TIME), claimsList);
  }

  public String createAccessToken(String subject, Map<String, Object> claimsList) {
    if (claimsList == null) {
      return createAccessTokenWithoutClaims(subject);
    } else {
      return createAccessTokenWithClaims(subject, claimsList);
    }
  }

}
