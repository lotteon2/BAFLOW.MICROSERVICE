package com.bit.lot.flower.auth.common.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public interface RefreshTokenStrategy {
  public void createRefreshToken(HttpServletRequest request);
  public void invalidateRefreshToken(HttpServletRequest request, HttpServletResponse response);
}