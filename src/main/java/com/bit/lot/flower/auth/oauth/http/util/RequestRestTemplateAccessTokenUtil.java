package com.bit.lot.flower.auth.oauth.http.util;

import com.bit.lot.flower.auth.oauth.dto.response.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class RequestRestTemplateAccessTokenUtil {

  private final RestTemplate restTemplate;

  public String request(String code, String clientId, String redirectURI, String requestURL) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-type", "application/x-www-form-urlencoded; charset=utf-8");

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

    body.add("grant_type", "authorization_code");
    body.add("client_id", clientId);
    body.add("redirect_uri", redirectURI);
    body.add("code", code);

    LoginResponseDto loginResponseDto = restTemplate.postForObject(
        requestURL,
        new HttpEntity<>(body, headers),
        LoginResponseDto.class);

    return loginResponseDto.getAccessToken();

  }
}
