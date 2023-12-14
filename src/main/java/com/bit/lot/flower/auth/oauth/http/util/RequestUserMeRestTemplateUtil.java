package com.bit.lot.flower.auth.oauth.http.util;


import com.bit.lot.flower.auth.oauth.dto.response.LoginResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class RequestUserMeRestTemplateUtil {

  private final RestTemplate restTemplate;

  public String getUserInfo(String accessCode,String userMeURL) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
    headers.add("Authorization", "Bearer " + accessCode);

    ResponseEntity<String> response =
        restTemplate.exchange(userMeURL,
            HttpMethod.GET,
            new HttpEntity<>(null, headers),
            String.class);

    return response.getBody();
  }




}
