package com.bit.lot.flower.auth.system.admin.security;

import com.bit.lot.flower.auth.common.valueobject.Role;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RequiredArgsConstructor
public class SystemAdminAuthenticationManager implements AuthenticationManager {

  @Value("${system.admin.id}")
  private final String adminId;
  @Value("${system.admin.password}")
  private final String adminPassword;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String inputId = (String) authentication.getPrincipal();
    String inputPassword = (String) authentication.getCredentials();
    if (!inputId.equals(adminId) || !inputPassword.equals(adminPassword)) {
      throw new BadCredentialsException("시스템 어드민의 아이디와 비밀번호가 일치하지 않습니다.");
    }

    return new UsernamePasswordAuthenticationToken(inputId, inputPassword,
        Collections.singleton(new SimpleGrantedAuthority(
            Role.ROLE_SYSTEM_ADMIN.toString())));
  }
}
