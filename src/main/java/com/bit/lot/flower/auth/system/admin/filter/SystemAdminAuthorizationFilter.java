package com.bit.lot.flower.auth.system.admin.filter;

import com.bit.lot.flower.auth.common.util.ExtractAuthorizationTokenUtil;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class SystemAdminAuthorizationFilter extends OncePerRequestFilter {

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String requestURI = request.getRequestURI();
    return !requestURI.contains("/admin") || requestURI.contains("/admin/login");
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    if (!Role.ROLE_SYSTEM_ADMIN.name().equals(getRole(request))) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      throw new InsufficientAuthenticationException("시스템 관리자의 권한이 없습니다. 시스템 관리자로 로그인해주세요");
    }
    doFilter(request, response, filterChain);

  }

  private String getRole(HttpServletRequest request) {
    String token = ExtractAuthorizationTokenUtil.extractToken(request);
    Claims claims = JwtUtil.extractClaims(token);
    return (String) claims.get(SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME);
  }

}
