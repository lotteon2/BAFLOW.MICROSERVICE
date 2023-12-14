package com.bit.lot.flower.auth.oauth.facade;

import com.bit.lot.flower.auth.common.valueobject.AuthenticationProvider;
import com.bit.lot.flower.auth.oauth.util.access.GetKakaoAccessKeyHttpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OauthLoginAccessTokenRequestFacade {
    private final GetKakaoAccessKeyHttpUtil getKakaoAccessKeyHttpUtil;
  public String request(AuthenticationProvider provider, String code) {
    if (provider.equals(AuthenticationProvider.kakao)) {
        getKakaoAccessKeyHttpUtil.getAccessToken(code);
    }
    throw new IllegalArgumentException("존재 하지 않는 인증 제공자입니다.");
  }

}
