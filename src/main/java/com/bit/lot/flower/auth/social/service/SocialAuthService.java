package com.bit.lot.flower.auth.social.service;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import org.springframework.stereotype.Service;

@Service
public interface SocialAuthService<ID extends AuthId>
{
  public void login(ID socialId);
  public void logout(ID socialId);
  public void userWithdrawalUserSelf(ID socialId);
}
