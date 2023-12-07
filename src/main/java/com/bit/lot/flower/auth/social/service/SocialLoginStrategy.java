package com.bit.lot.flower.auth.social.service;


import com.bit.lot.flower.auth.common.valueobject.BaseId;
import org.springframework.stereotype.Component;

@Component
public interface SocialLoginStrategy<ID extends BaseId> {
  public void login(ID socialId);
}
