package com.bit.lot.flower.auth.social.service;


import com.bit.lot.flower.auth.social.valueobject.SocialAuthId;
import org.springframework.stereotype.Component;

@Component
public interface SocialLoginStrategy {
  public void login(SocialAuthId socialId);
}
