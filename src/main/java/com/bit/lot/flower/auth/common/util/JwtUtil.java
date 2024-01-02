package com.bit.lot.flower.auth.common.util;


import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {


  public static String accessKey;
  public static String refreshKey;

  @Value("${encrypt.key.access}")
  private void setAccessKey(String accessKey) {
    JwtUtil.accessKey = accessKey;
  }

  @Value("${encrypt.key.refresh}")
  private void setRefreshKey(String refreshKey) {
    JwtUtil.refreshKey = refreshKey;
  }


  private JwtUtil() {

  }

  public static String generateAccessTokenWithClaims(String subject,
      Map<String, Object> claimsList) {
    Date now = new Date();
    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(now)
        .setExpiration(Date.from(Instant.now().plusSeconds(
            Long.parseLong(SecurityPolicyStaticValue.ACCESS_EXPIRATION_TIME))))
        .signWith(Keys.hmacShaKeyFor(accessKey.getBytes()), SignatureAlgorithm.HS256)
        .addClaims(claimsList)
        .compact();
  }

  public static String
  generateAccessToken(String subject) {
    Date now = new Date();
    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(now)
        .setExpiration(Date.from(Instant.now().plusSeconds(
            Long.parseLong(SecurityPolicyStaticValue.ACCESS_EXPIRATION_TIME))))
        .signWith(Keys.hmacShaKeyFor(accessKey.getBytes()), SignatureAlgorithm.HS256)
        .compact();
  }

  public static String generateRefreshToken(String subject) {
    Date now = new Date();

    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(now)
        .setExpiration(Date.from(Instant.now().plusSeconds(
            Long.parseLong(SecurityPolicyStaticValue.REFRESH_EXPIRATION_TIME))))
        .signWith(Keys.hmacShaKeyFor(refreshKey.getBytes()), SignatureAlgorithm.HS256)
        .compact();
  }

  public static Map<String, Object> addClaims(String id, Object value) {
    Map<String, Object> claims = new HashMap<>();
    claims.put(id, value);
    return claims;
  }

  public static String extractAccessTokenSubject(String token) {
    return extractAccessTokenClaims(token).getSubject();
  }

  public static Claims extractAccessTokenClaims(String accessToken) {
    return Jwts.parserBuilder()
        .setSigningKey(accessKey.getBytes()).
        build()
        .parseClaimsJws(accessToken)
        .getBody();
  }

  public static Claims extractRefreshToken(String refreshToken) {
    return Jwts.parserBuilder()
        .setSigningKey(refreshKey.getBytes()).
        build()
        .parseClaimsJws(refreshToken)
        .getBody();
  }

  public static boolean isAccessTokenValid(String accessToken) {
    try {
      extractAccessTokenClaims(accessToken);
      return true;
    } catch (ExpiredJwtException e) {
      throw new ExpiredJwtException(e.getHeader(), e.getClaims(), e.getMessage()) {
      };
    } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
      throw new IllegalArgumentException("올바르지 않은 접근입니다.");
    }
  }

  public static boolean isRefreshTokenValid(String refreshToken) {
    try {
      extractRefreshToken(refreshToken);
      return true;
    } catch (ExpiredJwtException e) {
      throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "refresh-token이 만료되었습니다.") {
      };
    } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
      throw new IllegalArgumentException("올바르지 않은 접근입니다.");
    }
  }


}

