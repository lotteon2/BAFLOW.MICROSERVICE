package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.social.repository.SocialAuthJpaRepository;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SocialAuthServiceImpl implements
    SocialAuthService<AuthId> {

  private final SocialLoginStrategy loginStrategy;
  private final SocialUserWithdrawalStrategy<AuthId> userWithdrawalStrategy;
  private final SocialLogoutStrategy<AuthId> logoutStrategy;
  private final SignUpStrategy<AuthId> signUpWhenUserIsNotExisted;
  private final SocialAuthJpaRepository repository;


  @Transactional
  @Override
  public void login(AuthId socialId) {
    loginStrategy.login(socialId);
  }

  @Override
  public void logout(AuthId socialId) {
    logoutStrategy.logout(socialId);
  }

  @Override
  public void userWithdrawalUserSelf(AuthId socialId) {
    logoutStrategy.logout(socialId);
    userWithdrawalStrategy.delete(socialId);
  }

}
