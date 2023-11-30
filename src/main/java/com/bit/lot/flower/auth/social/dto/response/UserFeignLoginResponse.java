package com.bit.lot.flower.auth.social.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserFeignLoginResponse{
  private String nickName;
  private boolean isPhoneNumberIsRegistered;
  private final String message ="유저 가져오기 성공";

}
