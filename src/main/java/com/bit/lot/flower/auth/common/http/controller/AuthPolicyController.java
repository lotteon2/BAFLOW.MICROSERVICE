package com.bit.lot.flower.auth.common.http.controller;

import com.bit.lot.flower.auth.common.dto.RenewAccessTokenDto;
import com.bit.lot.flower.auth.common.service.RenewRefreshTokenStrategy;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.common.valueobject.AuthId;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthPolicyController {

  private final RenewRefreshTokenStrategy<AuthId> renewRefreshTokenStrategy;

  @PostMapping("/api/auth/refresh-token")
  public ResponseEntity<String> renewRefreshToken(
      @RequestBody RenewAccessTokenDto<AuthId> renewAccessTokenDto, HttpServletRequest request,
      HttpServletResponse response) {
    String accessToken = renewRefreshTokenStrategy.renew(renewAccessTokenDto.getAuthId(),
        renewAccessTokenDto.getRole(),
        request, response);
    response.addHeader(SecurityPolicyStaticValue.TOKEN_AUTHORIZATION_HEADER_NAME,
        SecurityPolicyStaticValue.TOKEN_AUTHORIZATION_PREFIX + accessToken);
    return ResponseEntity.ok("재발급 완료");
  }


}
