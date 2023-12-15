package com.bit.lot.flower.auth.common.security;

import com.bit.lot.flower.auth.common.valueobject.AuthId;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtil {

  public static void setSecurityContextWithUserId(String userId) {
    Authentication authentication = new UsernamePasswordAuthenticationToken(new AuthId(Long.valueOf(userId)), null);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }



}
