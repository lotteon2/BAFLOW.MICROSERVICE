package com.bit.lot.flower.auth.common.security;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TokenHandler {

  private final RefreshTokenStrategy refreshTokenStrategy;
  private final JwtTokenProcessor jwtTokenProcessor;
  private final JwtAccessTokenDeleteStrategy deleteStrategy;


  public String createToken(String id,
      Map<String, Object> claimList,HttpServletResponse response) {
    String accessToken = jwtTokenProcessor.createAccessToken(id, claimList);
    refreshTokenStrategy.createRefreshToken(accessToken, response);

    return accessToken;

  }

  public void invalidateToken(String id, String token, HttpServletResponse response) {
    deleteStrategy.invalidateAccessToken(token);
    refreshTokenStrategy.invalidateRefreshToken(id, response);
  }


}
