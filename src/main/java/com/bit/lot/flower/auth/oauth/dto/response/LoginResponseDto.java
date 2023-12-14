package com.bit.lot.flower.auth.oauth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
  private String tokenType;
  private String accessToken;
  private String idToken;
  private String refreshToken;
  private int expiresIn;
  private int refreshTokenExpiresIn;
  private String scope;
}
