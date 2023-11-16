package com.bit.lot.flower.auth.system.admin.filter;

import com.bit.lot.flower.auth.common.util.ExtractAuthorizationTokenUtil;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import javax.naming.AuthenticationException;
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
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token = ExtractAuthorizationTokenUtil.extractToken(request);
    Claims claims = JwtUtil.extractClaims(token);
    String role = (String) claims.get(SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME);
    if(!Role.ROLE_SYSTEM_ADMIN.name().equals(role)){
      throw new InsufficientAuthenticationException("시스템 관리자의 권한이 없습니다. 시스템 관리자로 로그인해주세요");
    }

  }
}
