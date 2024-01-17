package com.bit.lot.flower.auth.common.security;

import com.bit.lot.flower.auth.common.util.CookieUtil;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.util.RedisRefreshTokenUtil;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import java.time.Duration;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class IssueRefreshTokenAtRedis implements RefreshTokenStrategy {


  private final RedisRefreshTokenUtil redisRefreshTokenUtil;

  @Override
  public void createRefreshToken(String accessToken, HttpServletResponse response) {
    String refreshToken = JwtUtil.generateRefreshToken(accessToken);
    redisRefreshTokenUtil.saveRefreshToken(accessToken, refreshToken,
        Long.parseLong(SecurityPolicyStaticValue.REFRESH_EXPIRATION_TIME)+30L);

  }

  @Override
  public void invalidateRefreshToken(String id, HttpServletResponse response) {
    redisRefreshTokenUtil.deleteRefreshToken(id);
  }


}
