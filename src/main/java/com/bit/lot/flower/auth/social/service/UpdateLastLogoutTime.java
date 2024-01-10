package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.social.exception.SocialAuthException;
import com.bit.lot.flower.auth.social.entity.SocialAuth;
import com.bit.lot.flower.auth.social.repository.SocialAuthJpaRepository;
import com.bit.lot.flower.auth.common.valueobject.AuthId;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateLastLogoutTime<ID extends AuthId> implements
    SocialLogoutStrategy<ID> {

  private final SocialAuthJpaRepository repository;

  @Override
  public void logout(ID socialId) {
    SocialAuth socialAuth = repository.findById(socialId.getValue()).orElseThrow(() -> {
      throw new SocialAuthException("존재하지 않은 유저입니다.");
    });
    repository.save(SocialAuth.builder().oauthId(socialAuth.getOauthId())
        .lastLogoutTime(
            ZonedDateTime.now()).build());
  }
}
