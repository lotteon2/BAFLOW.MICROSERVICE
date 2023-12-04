package com.bit.lot.flower.auth.social.security;

import com.bit.lot.flower.auth.common.valueobject.BaseId;
import com.bit.lot.flower.auth.social.exception.SocialAuthException;
import com.bit.lot.flower.auth.social.service.SocialLoginStrategy;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class SocialAuthenticationManager implements AuthenticationManager {


  private final SocialLoginStrategy<AuthId> socialLoginStrategy;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    try {
      socialLoginStrategy.login((AuthId) authentication.getPrincipal());
      return authentication;
    } catch (SocialAuthException e) {
      throw new SocialAuthException(e.getMessage());
    }

  }
}
