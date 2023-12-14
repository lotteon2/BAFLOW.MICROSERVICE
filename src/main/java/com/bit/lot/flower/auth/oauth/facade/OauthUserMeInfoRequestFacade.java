package com.bit.lot.flower.auth.oauth.facade;

import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.common.valueobject.AuthenticationProvider;
import com.bit.lot.flower.auth.oauth.dto.response.LoginResponseDto;
import com.bit.lot.flower.auth.oauth.http.util.RequestUserMeRestTemplateUtil;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OauthUserMeInfoRequestFacade {

  private final RequestUserMeRestTemplateUtil requestUserMeRestTemplateUtil;

  @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
  private String kakaoUserMeURL;

  public SocialLoginRequestCommand getUserInfo(String code, AuthenticationProvider provider)
      throws JsonProcessingException {
    if (provider.equals(AuthenticationProvider.kakao)) {
      return getKakaoUserData(requestUserMeRestTemplateUtil.getUserInfo(code, kakaoUserMeURL));
    }
    throw new IllegalArgumentException("존재 하지 않는 인증 제공자입니다.");
  }

  private SocialLoginRequestCommand getKakaoUserData(String data) throws JsonProcessingException {

    ObjectMapper mapper = new ObjectMapper();
    HashMap<String, Object> resultMap = mapper.readValue(data, HashMap.class);

    HashMap<String, Object> properties = (HashMap<String, Object>) resultMap.get("properties");
    HashMap<String, Object> kakaoAccount = (HashMap<String, Object>) resultMap.get("kakao_account");

    Long id = Long.valueOf(String.valueOf(resultMap.get("id")));
    String nickname = (String) properties.get("nickname");
    String email = (String) kakaoAccount.get("email");
    String phoneNumber = (String) kakaoAccount.get("phone_number");


    return new SocialLoginRequestCommand(new AuthId(id),email,phoneNumber,nickname);

  }

}
