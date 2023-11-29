package com.bit.lot.flower.auth.common.security;

import com.bit.lot.flower.auth.social.valueobject.AuthId;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtil {


  private static AuthId setStringPrincipalToAuthId(String userId){
    return AuthId.builder().value(Long.valueOf(userId)).build();
  }

  public static void setSecurityContextWithUserId(String userId) {
    Authentication authentication = new UsernamePasswordAuthenticationToken(setStringPrincipalToAuthId(userId), null);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }



}
