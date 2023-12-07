package com.bit.lot.flower.auth.social.http.helper;

import com.bit.lot.flower.auth.social.http.feign.OauthFeignClientRequest;
import com.bit.lot.flower.auth.common.valueobject.AuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OauthLogoutFacadeHelper {

  private final OauthFeignClientRequest feignClientRequest;

  @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
  private String clientId;
  @Value("${kakao.logout.redirect}")
  private String redirectURL;

  public void logout(AuthenticationProvider provider) {
           if (provider.equals(AuthenticationProvider.kakao)) {
      feignClientRequest.kakaoLogout(clientId,redirectURL);
           }
    }
  }


