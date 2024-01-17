package com.bit.lot.flower.auth.common.security;

import com.bit.lot.flower.auth.common.util.CookieUtil;
import com.bit.lot.flower.auth.common.util.ExtractAuthorizationTokenUtil;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.util.RedisRefreshTokenUtil;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class IssueRefreshRefreshTokenInCookie implements RefreshTokenStrategy {

  @Value("${cookie.refresh.http.life-time}")
  private Long lifeTime;
  @Value("${cookie.refresh.http.mall.domain}")
  private String mallDomain;
  @Value("${cookie.refresh.http.store.domain}")
  private String storeDomain;
  @Value("${cookie.refresh.http.admin.domain}")
  private String adminDomain;
  @Value("${cookie.refresh.token.name}")
  private String refreshCookieName;

  private final RedisRefreshTokenUtil redisRefreshTokenUtil;

  @Override
  public void createRefreshToken(String userId, Map<String, Object> claimList,
      HttpServletResponse response) {
    String refreshToken = JwtUtil.generateRefreshToken(String.valueOf(userId));
    redisRefreshTokenUtil.saveRefreshToken(userId, refreshToken,
        Long.parseLong(SecurityPolicyStaticValue.REFRESH_EXPIRATION_TIME));
    response.addCookie(CookieUtil.createCookie(refreshCookieName, refreshToken,
        lifeTime.intValue(), parseTheDomainByRole(claimList)));
  }

  @Override
  public void invalidateRefreshToken(String id,
      Map<String, Object> claimList, HttpServletResponse response) {
    CookieUtil.deleteCookie(response, refreshCookieName, parseTheDomainByRole(claimList));
    redisRefreshTokenUtil.deleteRefreshToken(id);
  }

  public String parseTheDomainByRole(Map<String, Object> claimList) {
    Role role = (Role) claimList.get("ROLE");
    if (role.equals(Role.ROLE_SOCIAL_USER)) {
      return mallDomain;
    } else if (role.equals(Role.ROLE_STORE_MANAGER)) {
      return storeDomain;
    } else {
      return adminDomain;
    }
  }


}
