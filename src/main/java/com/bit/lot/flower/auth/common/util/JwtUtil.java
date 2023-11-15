package com.bit.lot.flower.auth.common.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class JwtUtil {

  @Value("jwt.access.secret")
  private static String ACCESS_SECRET_KEY;
  @Value("jwt.access.lifetime")
  private static long ACCESS_EXPIRATION_TIME;
  @Value("jwt.refresh.lifetime")
  private static long REFRESH_EXPIRATION_TIME;
  @Value("jwt.refresh.secret")
  private static String REFRESH_SECRET_KEY;
  private static SecretKey refreshSecretKey;
  private static SecretKey accessSecretKey;

  public static String generateAccessToken(String subject) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + ACCESS_EXPIRATION_TIME);
    initKey();
    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(accessSecretKey)
        .compact();
  }

  public static String generateRefreshToken(String subject) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + REFRESH_EXPIRATION_TIME);
    initKey();
    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(refreshSecretKey)
        .compact();
  }

  public static String extractSubject(String token) {
    return extractClaims(token).getSubject();
  }

  private static Claims extractClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(accessSecretKey).build().parseClaimsJws(token)
        .getBody();
  }

  public static boolean isTokenValid(String token) {
    try {
      Claims claims = extractClaims(token);
      return !claims.getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }

  private static void initKey() {
    accessSecretKey = Keys.hmacShaKeyFor(ACCESS_SECRET_KEY.getBytes());
    refreshSecretKey = Keys.hmacShaKeyFor(REFRESH_SECRET_KEY.getBytes());
  }
}
