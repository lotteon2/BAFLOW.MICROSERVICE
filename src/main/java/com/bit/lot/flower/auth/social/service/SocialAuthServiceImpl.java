package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.social.entity.SocialAuth;
import com.bit.lot.flower.auth.social.repository.SocialAuthJpaRepository;
import com.bit.lot.flower.auth.social.valueobject.SocialAuthId;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SocialAuthServiceImpl implements
    SocialAuthService<SocialAuthId> {

  private final SocialUserWithdrawalStrategy<SocialAuthId> userWithdrawalStrategy;
  private final SocialLogoutStrategy<SocialAuthId> logoutStrategy;
  private final SignUpStrategy<SocialAuthId> signUpWhenUserIsNotExisted;
  private final SocialAuthJpaRepository repository;
  private final SocialAuthResignUpStrategy resignUpStrategy;


  @Transactional
  @Override
  public void login(SocialAuthId socialId) {
    Optional<SocialAuth> optionalSocialAuth = repository.findById(socialId.getValue());

    if (optionalSocialAuth.isEmpty()) {
      signUpWhenUserIsNotExisted.signUp(socialId);
    } else if (optionalSocialAuth.get().isRecentlyOut()) {
      resignUpStrategy.resignUp(optionalSocialAuth.get());
    }
  }

  @Override
  public void logout(SocialAuthId socialId) {
    logoutStrategy.logout(socialId);
  }

  @Override
  public void userWithdrawalUserSelf(SocialAuthId socialId) {
    logoutStrategy.logout(socialId);
    userWithdrawalStrategy.delete(socialId);
  }

}
