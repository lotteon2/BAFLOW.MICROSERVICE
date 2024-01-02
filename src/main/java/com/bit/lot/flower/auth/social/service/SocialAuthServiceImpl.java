package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.common.valueobject.AuthId;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SocialAuthServiceImpl<ID extends AuthId> implements
    SocialAuthService<ID> {

  private final SocialLoginStrategy<ID> loginStrategy;
  private final SocialUserWithdrawalStrategy<ID> userWithdrawalStrategy;
  private final SocialLogoutStrategy<ID> logoutStrategy;


  @Transactional
  @Override
  public void login(ID socialId) {
    loginStrategy.login(socialId);
  }

  @Override
  public void logout(ID socialId) {
    logoutStrategy.logout(socialId);
  }

  @Override
  public void userWithdrawalUserSelf(ID socialId) {
    logoutStrategy.logout(socialId);
    userWithdrawalStrategy.delete(socialId);
  }

}
