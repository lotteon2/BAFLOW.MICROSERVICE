package com.bit.lot.flower.auth.social.security;

import com.bit.lot.flower.auth.common.util.OauthInfoConvertor;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.exception.SocialAuthException;
import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.common.valueobject.AuthenticationProvider;
import java.util.LinkedHashMap;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

@Component
public class OauthUserInfoFacade {

  private AuthenticationProvider getProvider(DefaultOAuth2User defaultOAuth2User) {
    if (defaultOAuth2User.getAttributes().containsKey("kakao_account")) {
      return AuthenticationProvider.kakao;
    }
    return AuthenticationProvider.none;
  }

  public SocialLoginRequestCommand getCommand(DefaultOAuth2User defaultOAuth2User) {
    AuthenticationProvider provider = getProvider(defaultOAuth2User);
    return getOauth2LoginDto(defaultOAuth2User, provider);
  }

  private SocialLoginRequestCommand getOauth2LoginDto(DefaultOAuth2User oAuth2User,
      AuthenticationProvider provider) {
    if (provider == AuthenticationProvider.kakao) {
      return getKakaoDto(oAuth2User);
    }
    throw new SocialAuthException("아직 존재 하지 않는 인증 제공자입니다.");
  }


  private SocialLoginRequestCommand getKakaoDto(DefaultOAuth2User oAuth2User) {
    LinkedHashMap<String, String> kakaoAccount = oAuth2User.getAttribute("kakao_account");
    LinkedHashMap<String, String> properties = oAuth2User.getAttribute("properties");
    String id = oAuth2User.getName();
    String email = kakaoAccount.get("email");
    String phoneNumber = kakaoAccount.get("phone_number");
    String nickname = properties.get("nickname");
    return SocialLoginRequestCommand.builder().email(email).nickname(nickname)
        .phoneNumber(OauthInfoConvertor.convertInternationalPhoneNumberToDomestic(phoneNumber))
        .socialId(AuthId.builder().value(Long.valueOf(id)).build()).build();
  }



}