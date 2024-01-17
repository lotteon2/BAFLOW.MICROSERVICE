package com.bit.lot.flower.auth.social.http.filter;

import com.bit.lot.flower.auth.common.security.JwtTokenProcessor;
import com.bit.lot.flower.auth.common.security.SecurityContextUtil;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.social.exception.SocialAuthException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class SocialAuthorizationFilter extends OncePerRequestFilter {

  private final JwtTokenProcessor jwtTokenProcessor;


  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String requestURI = request.getRequestURI();
    return !requestURI.contains("/social") || requestURI.contains("/social/login");
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    if (!Role.ROLE_SOCIAL_USER.name().equals(jwtTokenProcessor.extractRole(request))) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      throw new SocialAuthException("유저의 권한이 없습니다.");
    }
    SecurityContextUtil.setSecurityContextWithUserId(
        jwtTokenProcessor.extractUserId(request));
    doFilter(request, response, filterChain);
  }


}
