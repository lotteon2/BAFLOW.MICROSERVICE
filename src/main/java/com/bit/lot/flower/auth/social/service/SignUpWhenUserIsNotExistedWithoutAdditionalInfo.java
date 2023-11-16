package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.social.entity.SocialAuth;
import com.bit.lot.flower.auth.social.repository.SocialAuthJpaRepository;
import com.bit.lot.flower.auth.social.valueobject.SocialAuthId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("SignUpWhenUserIsNotExistedWithoutAdditionalInfo")
public class SignUpWhenUserIsNotExistedWithoutAdditionalInfo implements
    SignUpStrategy<SocialAuthId> {

  private final SocialAuthJpaRepository repository;

  @Override
  public SocialAuth signUp(SocialAuthId socialId) {
    SocialAuth socialAuth = SocialAuth.builder().isRecentlyOut(false).lastLogoutTime(null)
        .oauthId(socialId.getValue()).build();
    return repository.save(socialAuth);
  }
}
