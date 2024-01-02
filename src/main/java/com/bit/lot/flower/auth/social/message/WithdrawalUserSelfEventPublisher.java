package com.bit.lot.flower.auth.social.message;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface WithdrawalUserSelfEventPublisher {
  public void delete(Long oauthId);
}
