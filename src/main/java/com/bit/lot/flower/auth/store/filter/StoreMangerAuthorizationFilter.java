package com.bit.lot.flower.auth.store.filter;

import com.bit.lot.flower.auth.common.util.ExtractAuthorizationTokenUtil;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.bit.lot.flower.auth.store.valueobject.StoreManagerStatus;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class StoreMangerAuthorizationFilter extends OncePerRequestFilter {

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String requestURI = request.getRequestURI();
    return requestURI.contains("/signup") || requestURI.contains("/business-number");
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    if (StoreManagerStatus.ROLE_STORE_MANAGER_PENDING.name().equals(getRole(request))) {
      throw new StoreManagerAuthException("시스템 관리자의 승인을 기다려주세요");
    }
  }

  private String getRole(HttpServletRequest request) {
    String token = ExtractAuthorizationTokenUtil.extractToken(request);
    Claims claims = JwtUtil.extractClaims(token);
    return (String) claims.get(SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME);
  }

}
