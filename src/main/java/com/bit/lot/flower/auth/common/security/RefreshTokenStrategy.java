package com.bit.lot.flower.auth.common.security;

import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public interface RefreshTokenStrategy {
  public void createRefreshToken(String userId, HttpServletResponse response);
  public void invalidateRefreshToken(String userId, HttpServletResponse response);
}