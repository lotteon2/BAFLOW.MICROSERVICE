package com.bit.lot.flower.auth.system.admin.filter;

import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.system.admin.exception.SystemAdminAuthException;
import java.util.Collections;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class SystemAdminAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  @Value("${system.admin.id}")
  private final String adminId;
  @Value("${system.admin.password}")
  private final String adminPassword;
  private final AuthenticationManager authenticationManager;
  private final TokenHandler tokenHandler;


  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) {
    try {
      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(adminId, adminPassword,
              Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_SYSTEM_ADMIN.name()))));

    } catch (AuthenticationException e) {
      throw new SystemAdminAuthException("존재하지 않는 시스템 어드민 유저입니다.");
    }

  }


  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult) {
      Map<String,Object> claimMap = JwtUtil.addClaims(SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME,
        Role.ROLE_SYSTEM_ADMIN.name());
    String token = tokenHandler.createToken(request,claimMap);
    response.addHeader(SecurityPolicyStaticValue.TOKEN_AUTHORIZAION_HEADER_NAME,token);
  }
}

