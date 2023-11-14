package com.bit.lot.flower.auth.social.service;

import org.springframework.stereotype.Service;

@Service
public interface SocialLogoutStrategy {

  public void logout(Long socialId);
}
