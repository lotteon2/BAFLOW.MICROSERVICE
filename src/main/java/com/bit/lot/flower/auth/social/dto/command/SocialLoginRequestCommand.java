package com.bit.lot.flower.auth.social.dto.command;

import com.bit.lot.flower.auth.common.valueobject.BaseId;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class
SocialLoginRequestCommand<ID extends BaseId> {
  private ID socialId;
  private String email;
  private String nickname;
}
