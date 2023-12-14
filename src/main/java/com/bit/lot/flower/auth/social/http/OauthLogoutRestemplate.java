package com.bit.lot.flower.auth.social.http;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class OauthLogoutRestemplate {

  private final RestTemplate restTemplate;

  public ResponseEntity<String> logout(String domain ,String clientId, String redirectURL) {
    String url =
        domain + "?client_id=" + clientId + "&logout_redirect_uri=" + redirectURL;
    return restTemplate.getForEntity(url, String.class);
  }
}
