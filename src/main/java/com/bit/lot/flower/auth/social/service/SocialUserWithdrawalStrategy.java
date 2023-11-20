package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.social.valueobject.AuthId;
import org.springframework.stereotype.Component;

@Component
public interface SocialUserWithdrawalStrategy<ID extends AuthId> {
  public void delete(ID userId);
}
