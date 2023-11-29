package com.bit.lot.flower.auth.social.service;


import com.bit.lot.flower.auth.common.valueobject.BaseId;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import org.springframework.stereotype.Component;

@Component
public interface SocialLoginStrategy<ID extends BaseId> {
  public void login(ID socialId);
}
