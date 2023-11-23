package com.bit.lot.flower.auth.common.security;

import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TokenHandler {

  private final RefreshTokenStrategy refreshTokenStrategy;
  private final JwtAccessTokenCreateProcessor accessTokenStrategy;
  private final JwtAccessTokenDeleteStrategy deleteStrategy;

  public String createToken(String id,
      Map<String, Object> claimList,HttpServletResponse response) {
    refreshTokenStrategy.createRefreshToken(id,response);
    return accessTokenStrategy.createAccessToken(id, claimList);
  }

  public void invalidateToken(String id, HttpServletResponse response) {
    deleteStrategy.invalidateAccessToken(id);
    refreshTokenStrategy.invalidateRefreshToken(id, response);
  }
}
