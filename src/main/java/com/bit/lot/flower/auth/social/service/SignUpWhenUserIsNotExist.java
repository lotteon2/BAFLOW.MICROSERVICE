package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.social.entity.SocialAuth;
import com.bit.lot.flower.auth.social.repository.SocialAuthJpaRepository;
import com.bit.lot.flower.auth.common.valueobject.AuthId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class SignUpWhenUserIsNotExist<ID extends AuthId> implements
    SocialLoginStrategy<ID> {

  private final SocialAuthJpaRepository repository;
  private final SocialAuthResignUpStrategy resignUpStrategy;
  private final SignUpStrategy<AuthId> signUpWhenUserIsNotExisted;

  @Override
  public void login(ID socialId) {
    Optional<SocialAuth> optionalSocialAuth = repository.findById(socialId.getValue());
    if (optionalSocialAuth.isEmpty()) {
      signUpWhenUserIsNotExisted.signUp(socialId);
    } else if (optionalSocialAuth.get().isRecentlyOut()) {
      resignUpStrategy.resignUp(optionalSocialAuth.get());
    }
  }
}
