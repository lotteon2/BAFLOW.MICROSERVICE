package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.social.SocialAuthException;
import com.bit.lot.flower.auth.social.entity.SocialAuth;
import com.bit.lot.flower.auth.social.repository.SocialAuthJpaRepository;
import com.bit.lot.flower.auth.social.valueobject.SocialAuthId;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateLastLogoutTime implements
    SocialLogoutStrategy<SocialAuthId> {

  private final SocialAuthJpaRepository repository;

  @Override
  public void logout(SocialAuthId socialId) {
    SocialAuth socialAuth = repository.findById(socialId.getValue()).orElseThrow(() -> {
      throw new SocialAuthException("존재하지 않은 유저입니다.");
    });
    repository.save(SocialAuth.builder().oauthId(socialAuth.getOauthId())
        .isRecentlyOut(socialAuth.isRecentlyOut()).lastLogoutTime(
            ZonedDateTime.now()).build());
  }
}
