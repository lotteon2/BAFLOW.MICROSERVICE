package com.bit.lot.flower.auth.common.http.interceptor.filter;

import com.bit.lot.flower.auth.common.security.JwtTokenProcessor;
import com.bit.lot.flower.auth.common.util.ExtractAuthorizationTokenUtil;
import com.bit.lot.flower.auth.common.util.RedisBlackListTokenUtil;
import com.bit.lot.flower.auth.common.valueobject.JWTAuthenticationShouldNotFilterAntMatcher;
import com.bit.lot.flower.auth.common.valueobject.KakaoOAuthURLAntURI;
import com.bit.lot.flower.auth.common.valueobject.SwaggerRequestURI;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final RedisBlackListTokenUtil redisBlackListTokenUtil;
  private final JwtTokenProcessor jwtTokenProcessor;
  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return shouldNotFilterSwaggerURI(request) || shouldNotFilterKakaoOauth2(request)
        || shouldNotFilterBySystemPolicy(request);
  }

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token = ExtractAuthorizationTokenUtil.extractToken(request);
    if (redisBlackListTokenUtil.isTokenBlacklisted(token)) {
      throw new AuthenticationException("해당 토큰은 이미 로그아웃 처리된 토큰이라 사용할 수 없는 토큰입니다.");
    }
    try {
      jwtTokenProcessor.validateAccessToken(token);
    } catch (ExpiredJwtException e) {
      response.setStatus(401);
      throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "Expired");
    }
    filterChain.doFilter(request, response);
  }

  private boolean shouldNotFilterSwaggerURI(HttpServletRequest request) {
    String requestURI = request.getRequestURI();
    return requestURI.contains(SwaggerRequestURI.UI_URI) || requestURI.contains(
        SwaggerRequestURI.API_DOCS_URI)
        || requestURI.contains(SwaggerRequestURI.WEB_JARS) || requestURI.contains(
        SwaggerRequestURI.FAVICON)
        || requestURI.contains(SwaggerRequestURI.RESOURCES);
  }

  private boolean shouldNotFilterKakaoOauth2(HttpServletRequest request) {

    String requestURI = request.getRequestURI();
    return requestURI.contains(KakaoOAuthURLAntURI.KAPI)
        || requestURI.contains(KakaoOAuthURLAntURI.KAUTH) || requestURI.contains(
        KakaoOAuthURLAntURI.REDIRECT)
        || requestURI.contains(KakaoOAuthURLAntURI.OAUTH);
  }

  private boolean shouldNotFilterBySystemPolicy(HttpServletRequest request) {
    String requestURI = request.getRequestURI();
    return requestURI.contains(JWTAuthenticationShouldNotFilterAntMatcher.SIGNUP_ANT)
        || requestURI.contains(JWTAuthenticationShouldNotFilterAntMatcher.LOGIN_ANT)
        || requestURI.contains(JWTAuthenticationShouldNotFilterAntMatcher.EMAIL_ANT)
        || requestURI.contains(JWTAuthenticationShouldNotFilterAntMatcher.REFRESH_ANT);

  }


}