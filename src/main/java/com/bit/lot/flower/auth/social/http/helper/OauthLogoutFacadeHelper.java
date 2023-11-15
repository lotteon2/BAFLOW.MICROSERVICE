package com.bit.lot.flower.auth.social.http.helper;

import com.bit.lot.flower.auth.social.http.feign.OauthFeignClientRequest;
import com.bit.lot.flower.auth.social.valueobject.AuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OauthLogoutFacadeHelper {

  private final OauthFeignClientRequest feignClientRequest;

  public void logout(AuthenticationProvider provider,String accessToken) {
    if (provider.equals(AuthenticationProvider.kakao)) {
        feignClientRequest.kakaoLogout("Bearer " + accessToken);
    }
  }
}
