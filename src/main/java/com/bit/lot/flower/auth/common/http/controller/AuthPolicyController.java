package com.bit.lot.flower.auth.common.http.controller;

import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.service.RenewRefreshTokenStrategy;
import com.bit.lot.flower.auth.social.valueobject.AuthenticationProvider;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthPolicyController {

  private final RenewRefreshTokenStrategy renewRefreshTokenStrategy;
  private final TokenHandler tokenHandler;

  @PostMapping("/api/refresh-token")
  public ResponseEntity<String> renewRefreshToken(HttpServletRequest request) {

  }

  @PostMapping("/api/authentication-provider/{AuthenticationProvider}/code")
  public ResponseEntity<String> verifyOauth2User(HttpServletRequest request,
      AuthenticationProvider provider) {

  }


}
