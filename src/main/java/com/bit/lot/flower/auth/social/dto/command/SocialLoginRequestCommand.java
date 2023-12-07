package com.bit.lot.flower.auth.social.dto.command;

import com.bit.lot.flower.auth.common.valueobject.AuthId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SocialLoginRequestCommand {
  private AuthId socialId;
  private String email;
  private String phoneNumber;
  private String nickname;
}
