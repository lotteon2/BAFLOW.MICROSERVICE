package com.bit.lot.flower.auth.social.http.helper;

import com.bit.lot.flower.auth.social.service.SocialAuthService;
import com.bit.lot.flower.auth.social.valueobject.AuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OauthLoginFacadeHelper {

  private final SocialAuthService socialAuthService;
  public void login(AuthenticationProvider provider, String id) {
    if (provider.equals(AuthenticationProvider.kakao)) {

    }
  }
}
