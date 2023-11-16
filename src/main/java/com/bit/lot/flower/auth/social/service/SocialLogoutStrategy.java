package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.social.valueobject.SocialAuthId;
import org.springframework.stereotype.Service;

@Service
public interface SocialLogoutStrategy<ID extends SocialAuthId> {

  public void logout(ID socialId);
}
