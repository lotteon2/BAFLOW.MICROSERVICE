package com.bit.lot.flower.auth.social.service;
import com.bit.lot.flower.auth.common.valueobject.BaseId;
import org.springframework.stereotype.Service;

@Service
public interface SocialAuthService<ID extends BaseId>
{
  public void login(ID socialId);
  public void logout(ID socialId);
  public void userWithdrawalUserSelf(ID socialId);
}
