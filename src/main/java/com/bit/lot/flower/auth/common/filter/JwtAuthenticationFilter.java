package com.bit.lot.flower.auth.common.filter;

import com.bit.lot.flower.auth.common.util.ExtractAuthorizationTokenUtil;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.util.RedisBlackListTokenUtil;
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

  private boolean shouldNotFilterSwaggerURI(HttpServletRequest request)  {
    String requestURI = request.getRequestURI();
    return requestURI.contains("/swagger-ui.html") || requestURI.contains("/v2/api-docs")
        || requestURI.contains("/webjars") || requestURI.contains("/swagger-resources")
        || requestURI.contains("/favicon");
  }

  private boolean shouldNotFilterOauth2(HttpServletRequest request)  {

    String requestURI = request.getRequestURI();
    return requestURI.contains("/kauth")
        || requestURI.contains("/oauth") || requestURI.contains("/")
        || requestURI.contains("/kapi");
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String requestURI = request.getRequestURI();
    return shouldNotFilterSwaggerURI(request) || shouldNotFilterOauth2(request)
        || requestURI.contains("/signup")
        || requestURI.contains("/login")  || requestURI.contains("/emails");
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token = ExtractAuthorizationTokenUtil.extractToken(request);
    if (redisBlackListTokenUtil.isTokenBlacklisted(token)) {
      throw new AuthenticationException("해당 토큰은 이미 로그아웃 처리된 토큰이라 사용할 수 없는 토큰입니다.");
    }
    JwtUtil.isTokenValid(token);
    filterChain.doFilter(request, response);
  }
}