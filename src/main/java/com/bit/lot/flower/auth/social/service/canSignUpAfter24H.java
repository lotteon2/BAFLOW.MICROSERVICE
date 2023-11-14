package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.social.SocialAuthException;
import com.bit.lot.flower.auth.social.entity.SocialAuth;
import com.bit.lot.flower.auth.social.repository.SocialAuthJpaRepository;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class canSignUpAfter24H implements
    SocialAuthResignUpStrategy {

  private final long RESIGN_UP_POSSIBLE_TIME_AFTER_WITHDRAWAL =24 ;
  private final SocialAuthJpaRepository repository;
  @Override
  public void resignUp(SocialAuth socialAuth) {
    if(socialAuth.isRecentlyOut()){
      if(!socialAuth.getLastLogoutTime().plusHours(RESIGN_UP_POSSIBLE_TIME_AFTER_WITHDRAWAL).isAfter(
          ZonedDateTime.now())){
        throw new SocialAuthException("24시간 이내에는 재 회원가입을 할 수 없습니다.");
      }
      else{
        repository.save(socialAuth);
      }
    }
  }
}
