package com.bit.lot.flower.auth.common.security;

import javax.servlet.http.HttpServletRequest;

public interface JwtAccessTokenDeleteStrategy {
  public void invalidateAccessToken(HttpServletRequest request);

}
