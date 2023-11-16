package com.bit.lot.flower.auth.social.dto.command;

import com.bit.lot.flower.auth.social.valueobject.SocialAuthId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SocialLoginRequestCommand {
  private SocialAuthId socialId;
  private String email;
  private String nickname;
}
