package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.social.dto.response.LoginSuccessResponse;
import com.bit.lot.flower.auth.social.entity.SocialAuth;
import org.springframework.stereotype.Service;

@Service
public interface SocialAuthService {
  public void login(Long socialId);
  public void logout(Long socialId);
}
