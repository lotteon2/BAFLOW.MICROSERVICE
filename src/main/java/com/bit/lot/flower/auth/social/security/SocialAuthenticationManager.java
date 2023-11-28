package com.bit.lot.flower.auth.social.security;


import com.bit.lot.flower.auth.social.entity.SocialAuth;
import com.bit.lot.flower.auth.social.exception.SocialAuthException;
import com.bit.lot.flower.auth.social.repository.SocialAuthJpaRepository;
import com.bit.lot.flower.auth.social.service.SocialLoginStrategy;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;


@RequiredArgsConstructor
public class SocialAuthenticationManager implements AuthenticationManager {

  private final SocialLoginStrategy socialLoginStrategy;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    try {
      String oauthId = getUserOauth2Id();
      socialLoginStrategy.login((getAuthId(oauthId)));
      return new UsernamePasswordAuthenticationToken(oauthId, null,
          Collections.singleton(new SimpleGrantedAuthority("ROLE_SOCIAL_USER")));
    } catch (SocialAuthException socialAuthException) {
      throw new SocialAuthException(socialAuthException.getMessage());
    }
  }

  private AuthId getAuthId(String id) {
    return AuthId.builder().value(Long.valueOf(id)).build();
  }

  private String getUserOauth2Id() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
    return defaultOAuth2User.getName();
  }

}
