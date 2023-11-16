package com.bit.lot.flower.auth.social.service;
import com.bit.lot.flower.auth.social.valueobject.SocialAuthId;
import org.springframework.stereotype.Service;

@Service
public interface SocialAuthService<ID extends SocialAuthId>
{
  public void login(ID socialId);
  public void logout(ID socialId);
  public void userWithdrawalUserSelf(ID socialId);
}
