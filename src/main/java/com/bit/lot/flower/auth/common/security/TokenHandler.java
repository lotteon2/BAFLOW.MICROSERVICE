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

  public String createToken(HttpServletRequest request,
      Map<String, Object> claimList) {
    refreshTokenStrategy.createRefreshToken(request);
    return accessTokenStrategy.createAccessToken(
        request.getHeader(SecurityPolicyStaticValue.USER_ID_HEADER_NAME), claimList);
  }

  public void invalidateToken(HttpServletRequest request, HttpServletResponse response) {
    deleteStrategy.invalidateAccessToken(request);
    refreshTokenStrategy.invalidateRefreshToken(request, response);
  }
}
