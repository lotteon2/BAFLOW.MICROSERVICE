package com.bit.lot.flower.auth.oauth;

import com.bit.lot.flower.auth.common.valueobject.AuthenticationProvider;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class OauthLoginCodeRequestFacade {

  private final RestTemplate restTemplate;


  private String kakaoClientKey;

  public SocialLoginRequestCommand request(AuthenticationProvider provider, String code) {
    if (provider.equals(AuthenticationProvider.kakao)) {
      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

      body.add("grant_type", "authorization_code");
      body.add("client_id", kakaoKey);
      body.add("redirect_uri", "http://localhost:8080/openapi/kakao");
      body.add("code", code);

      LoginResponseDto loginResponseDto = restTemplate.postForObject(
          "https://kauth.kakao.com/oauth/token",
          new HttpEntity<>(body, headers),
          LoginResponseDto.class);
    }
  }

  private SocialLoginRequestCommand kakaoRequest(String code) {

  }
}
