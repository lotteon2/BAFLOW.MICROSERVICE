package com.bit.lot.flower.auth.common.security;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public interface RefreshTokenStrategy {

  void createRefreshToken(String userId, Map<String, Object> claimList,
      HttpServletResponse response);

  void invalidateRefreshToken(String userId,
      Map<String, Object> claimList, HttpServletResponse response);
}