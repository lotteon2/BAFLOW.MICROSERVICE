package com.bit.lot.flower.auth.social.service;

import com.bit.lot.flower.auth.social.entity.SocialAuth;
import com.bit.lot.flower.auth.social.valueobject.SocialAuthId;
import org.springframework.stereotype.Service;

@Service
public interface SignUpStrategy<ID extends SocialAuthId> {

  public SocialAuth signUp(ID socialId);

}
