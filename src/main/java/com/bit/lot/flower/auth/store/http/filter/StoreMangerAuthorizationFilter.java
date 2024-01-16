package com.bit.lot.flower.auth.store.http.filter;

import com.bit.lot.flower.auth.common.security.JwtTokenProcessor;
import com.bit.lot.flower.auth.common.security.SecurityContextUtil;
import com.bit.lot.flower.auth.common.util.ExtractAuthorizationTokenUtil;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.bit.lot.flower.auth.store.valueobject.StoreManagerStatus;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class StoreMangerAuthorizationFilter extends OncePerRequestFilter {

  private final JwtTokenProcessor jwtTokenProcessor;
  @Override
  protected boolean shouldNotFilter(HttpServletRequest request)  {
    String requestURI = request.getRequestURI();
    return !requestURI.contains("/stores") || requestURI.contains("/stores/login") ||
        requestURI.contains("/stores/signup") ||
        requestURI.contains("/stores/emails");
  }


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    if (StoreManagerStatus.ROLE_STORE_MANAGER_PENDING.name().equals(
        jwtTokenProcessor.extractRole(request))) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      throw new StoreManagerAuthException("시스템 관리자의 승인을 기다려주세요");
    } else if (StoreManagerStatus.ROLE_STORE_MANAGER_DENIED.name()
        .equals(jwtTokenProcessor.extractRole(request))) {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      throw new StoreManagerAuthException("잘못된 사업자등록증 번호입니다.");
    }
    SecurityContextUtil.setSecurityContextWithUserId(
        jwtTokenProcessor.extractUserId(request));
    doFilter(request, response, filterChain);
  }


}
