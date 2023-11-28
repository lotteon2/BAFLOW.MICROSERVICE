package com.bit.lot.flower.auth.social.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserFeignLoginResponse<ID extends com.bit.lot.flower.auth.social.http.valueobject.UserId> {
  private ID userId;
  private boolean isPhoneNumberIsRegistered;
  private final String message ="유저 가져오기 성공";

}
