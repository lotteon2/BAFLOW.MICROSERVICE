package com.bit.lot.flower.auth.common.service;

import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.common.valueobject.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class RenewRefreshTokenWhenAccessTokenExpired implements
    RenewRefreshTokenStrategy<AuthId> {

  @Override
  public String renew(AuthId id, Role role, String expiredToken, HttpServletRequest request,
      HttpServletResponse response) {
    return null;
  }
}
