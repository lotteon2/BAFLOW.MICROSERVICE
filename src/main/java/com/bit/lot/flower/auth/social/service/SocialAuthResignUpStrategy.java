package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.social.entity.SocialAuth;
import org.springframework.stereotype.Service;

@Service
public interface SocialAuthResignUpStrategy {

  public void resignUp(SocialAuth socialAuth);
}
