package com.bit.lot.flower.auth.social.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginSuccessResponse {
  private boolean isPhoneNumberIsRegistered;
  private final String message ="로그인 완료";
}
