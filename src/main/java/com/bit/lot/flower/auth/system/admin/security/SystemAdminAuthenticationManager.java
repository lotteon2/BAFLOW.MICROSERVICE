package com.bit.lot.flower.auth.system.admin.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class SystemAdminAuthenticationManager implements AuthenticationManager {

  @Value("${system.admin.id}")
  private final Long adminId;
  @Value("${system.admin.password}")
  private final String adminPassword;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    Long inputId = (Long) authentication.getPrincipal();
    String inputPassword = (String) authentication.getCredentials();
    if (!inputId.equals(adminId) && !inputPassword.equals(adminPassword)) {
      throw new BadCredentialsException("시스템 어드민의 아이디와 비밀번호가 일치하지 않습니다.");
    }

    return authentication;
  }
}
