package com.bit.lot.flower.auth.common.security;


public interface JwtAccessTokenDeleteStrategy {
  public void invalidateAccessToken(String token);

}
