package com.bit.lot.flower.auth.oauth.util.access;

import com.bit.lot.flower.auth.oauth.http.util.RequestRestTemplateAccessTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class GetKakaoAccessKeyHttpUtil {

  private final RequestRestTemplateAccessTokenUtil requestRestTemplateAccessTokenUtil;

  private final String requestURI = "https://kauth.kakao.com/oauth/token";
  @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
  private String clientId;
  @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
  private String redirectURI;

  public String getAccessToken(String code) {
    return requestRestTemplateAccessTokenUtil.request(code, clientId, redirectURI, requestURI);
  }

}
