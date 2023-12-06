package com.bit.lot.flower.auth.common.util;

import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletRequest;

public class ExtractAuthorizationTokenUtil {


  private ExtractAuthorizationTokenUtil() {

  }

  public static String extractToken(HttpServletRequest request) {

    String authorizationHeader = request.getHeader(
        SecurityPolicyStaticValue.TOKEN_AUTHORIZAION_HEADER_NAME);
    if (authorizationHeader != null && authorizationHeader.startsWith(SecurityPolicyStaticValue.TOKEN_AUTHORIZATION_PREFIX)) {
      return authorizationHeader.substring(7);
    } else {
      throw new IllegalArgumentException("토큰 정보를 찾을 수 없습니다. 로그인을 먼저 해주세요.");
    }
  }

  public static String extractUserId(HttpServletRequest request){
    String token = extractToken(request);
    return JwtUtil.extractSubject(token);
  }

    public static String extractRole(HttpServletRequest request) {
    String token = ExtractAuthorizationTokenUtil.extractToken(request);
    Claims claims = JwtUtil.extractClaims(token);
    return (String) claims.get(SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME);
  }

}
