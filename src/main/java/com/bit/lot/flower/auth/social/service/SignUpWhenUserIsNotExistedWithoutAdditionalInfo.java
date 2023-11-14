package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.social.entity.SocialAuth;
import com.bit.lot.flower.auth.social.repository.SocialAuthJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("SignUpWhenUserIsNotExistedWithoutAdditionalInfo")
public class SignUpWhenUserIsNotExistedWithoutAdditionalInfo implements
    SignUpStrategy {

  private final SocialAuthJpaRepository repository;

  @Override
  public SocialAuth signUp(Long socialId) {
    SocialAuth socialAuth = SocialAuth.builder().isRecentlyOut(false).lastLogoutTime(null)
        .oauthId(socialId).build();
    return repository.save(socialAuth);
  }
}
