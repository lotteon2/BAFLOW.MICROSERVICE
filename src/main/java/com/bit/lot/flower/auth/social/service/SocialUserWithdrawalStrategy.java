package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.common.valueobject.BaseId;
import org.springframework.stereotype.Component;

@Component
public interface SocialUserWithdrawalStrategy<ID extends BaseId> {
  public void delete(ID userId);
}
