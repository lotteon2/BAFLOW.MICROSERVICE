package com.bit.lot.flower.auth.social.dto;

import javax.ws.rs.GET;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserFeignLoginResponse<UserId> {
  private UserId userId;
  private boolean isPhoneNumberIsRegistered;
  private final String message ="유저 가져오기 성공";

}
