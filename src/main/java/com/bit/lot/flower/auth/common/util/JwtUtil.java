package com.bit.lot.flower.auth.common.util;


import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class JwtUtil {

  @Value("jwt.access.secret")
  private static String ACCESS_SECRET_KEY;
  @Value("jwt.refresh.secret")
  private static String REFRESH_SECRET_KEY;
  private static SecretKey refreshSecretKey;
  private static SecretKey accessSecretKey;

  public static String generateAccessTokenWithClaims(String subject,
      Map<String, Object> claimsList) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + SecurityPolicyStaticValue.ACCESS_EXPIRATION_TIME);
    initKey();
    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(accessSecretKey)
        .addClaims(claimsList)
        .compact();
  }

  public static Map<String,Object> addClaims(String id, Object value){
    Map<String,Object> claims =new HashMap<>();
    claims.put(id,value);
      return claims;
  }

  public static String generateAccessToken(String subject) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + SecurityPolicyStaticValue.ACCESS_EXPIRATION_TIME);
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
    Date expiryDate = new Date(now.getTime() + SecurityPolicyStaticValue.REFRESH_EXPIRATION_TIME);
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
