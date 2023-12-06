package com.bit.lot.flower.auth.common.util;


import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtUtil {

  private static SecretKey accessSecret;
  private static SecretKey refreshSecret;



  public static String generateAccessTokenWithClaims(String subject,
      Map<String, Object> claimsList) {
    Date now = new Date();

    initAccessKey();
    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(now)
        .setExpiration(Date.from(Instant.now().plusMillis(
            Long.parseLong(SecurityPolicyStaticValue.ACCESS_EXPIRATION_TIME))))
        .signWith(accessSecret)
        .addClaims(claimsList)
        .compact();
  }

  public static Map<String, Object> addClaims(String id, Object value) {
    Map<String, Object> claims = new HashMap<>();
    claims.put(id, value);
    return claims;
  }

  public static String generateAccessToken(String subject) {
    Date now = new Date();

    initAccessKey();
    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(now)
        .setExpiration(Date.from(Instant.now().plusMillis(
            Long.parseLong(SecurityPolicyStaticValue.ACCESS_EXPIRATION_TIME))))
        .signWith(accessSecret)
        .compact();
  }

  public static String generateRefreshToken(String subject) {
    Date now = new Date();
    initRefreshKey();

    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(now)
        .setExpiration(Date.from(Instant.now().plusMillis(
            Long.parseLong(SecurityPolicyStaticValue.REFRESH_EXPIRATION_TIME))))
        .signWith(refreshSecret)
        .compact();
  }

  public static String extractSubject(String token) {
    return extractClaims(token).getSubject();
  }

  public static Claims extractClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(accessSecret).build().parseClaimsJws(token)
        .getBody();
  }

  public static void isTokenValid(String token) {
    try {
      extractClaims(token);
    } catch (ExpiredJwtException expiredJwtException) {
      throw new JwtException("토큰 시간이 만료되었습니다.") {
      };
    } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
      throw new IllegalArgumentException("올바르지 않은 접근입니다.");
    }
  }

    private static void initRefreshKey() {
      try {
        refreshSecret = KeyGenerator.getInstance("HmacSHA256").generateKey();

      } catch (NoSuchAlgorithmException e) {
        throw new IllegalArgumentException("plz init the secret key");
      }
    }


    private static void initAccessKey() {
      try {
        accessSecret = KeyGenerator.getInstance("HmacSHA256").generateKey();

      } catch (NoSuchAlgorithmException e) {
        throw new IllegalArgumentException("plz init the secret key");
      }

    }
  }
