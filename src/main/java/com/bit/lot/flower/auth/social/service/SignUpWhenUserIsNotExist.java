package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.common.security.JwtAccessTokenCreateProcessor;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.social.entity.SocialAuth;
import com.bit.lot.flower.auth.social.repository.SocialAuthJpaRepository;
import com.bit.lot.flower.auth.social.valueobject.SocialAuthId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class SignUpWhenUserIsNotExist implements
    SocialLoginStrategy {

  private final SocialAuthJpaRepository repository;
  private final SocialAuthResignUpStrategy resignUpStrategy;
  private final SignUpStrategy<SocialAuthId> signUpWhenUserIsNotExisted;

  @Override
  public void login(SocialAuthId socialId) {
    Optional<SocialAuth> optionalSocialAuth = repository.findById(socialId.getValue());
    if (optionalSocialAuth.isEmpty()) {
      signUpWhenUserIsNotExisted.signUp(socialId);
    } else if (optionalSocialAuth.get().isRecentlyOut()) {
      resignUpStrategy.resignUp(optionalSocialAuth.get());
    }
  }
}
