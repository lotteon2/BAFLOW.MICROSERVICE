package com.bit.lot.flower.auth.social.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserLoginResponse {
  private String nickName;
  private boolean isPhoneNumberIsRegistered;
}
