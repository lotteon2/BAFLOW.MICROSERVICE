package com.bit.lot.flower.auth.social.security;

import com.bit.lot.flower.auth.social.exception.SocialAuthException;
import com.bit.lot.flower.auth.social.service.SocialLoginStrategy;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class SocialAuthenticationManager implements AuthenticationManager {


  private final SocialLoginStrategy socialLoginStrategy;

  private AuthId convertPrincipalToAuthId(Authentication authentication) {
    Long authIdValue = Long.valueOf(String.valueOf(authentication.getPrincipal()));
    return AuthId.builder().value(authIdValue).build();
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    try {
      socialLoginStrategy.login(convertPrincipalToAuthId(authentication));
      return authentication;
    } catch (SocialAuthException e) {
      throw new SocialAuthException(e.getMessage());
    }

  }
}
