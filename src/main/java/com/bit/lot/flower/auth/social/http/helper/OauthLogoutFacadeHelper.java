package com.bit.lot.flower.auth.social.http.helper;

import com.bit.lot.flower.auth.common.valueobject.AuthenticationProvider;
import com.bit.lot.flower.auth.social.http.OauthLogoutRestemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OauthLogoutFacadeHelper {

  private final OauthLogoutRestemplate oauthLogoutRestemplate;
  @Value("${kakao.logout.redirect}")
  private String redirectURL;
  @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
  private String kakaoClientId;

  public void logout(AuthenticationProvider provider) {

    if (provider.equals(AuthenticationProvider.kakao)) {
      oauthLogoutRestemplate.logout("https://kauth.kakao.com", kakaoClientId, redirectURL);
    }

  }
}

