package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.social.entity.SocialAuth;
import com.bit.lot.flower.auth.social.repository.SocialAuthJpaRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SocialAuthServiceImpl implements
    SocialAuthService {


  private final SocialLogoutStrategy logoutStrategy;
  private final SocialAuthResignUpStrategy resignUpStrategy;
  private final SignUpStrategy signUpWhenUserIsNotExisted;
  private final SocialAuthJpaRepository repository;



  @Transactional
  @Override
  public void login(Long socialId) {
    Optional<SocialAuth> optionalSocialAuth = repository.findById(socialId);

    if(optionalSocialAuth.isEmpty()) {
      signUpWhenUserIsNotExisted.signUp(socialId);
    }
    /*
    isEmpty가 아니라는 조건은 해당 유저가 존재한다는 것을 의미하므로, 최근 회원탈퇴 전략을 위반하지 않는다면
     */
    else if(optionalSocialAuth.get().isRecentlyOut()){
      resignUpStrategy.resignUp(optionalSocialAuth.get());
    }
  }

  @Override
  public void logout(Long socialId) {
    logoutStrategy.logout(socialId);
  }
}
