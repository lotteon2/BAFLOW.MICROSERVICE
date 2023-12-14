package com.bit.lot.flower.auth.social.dto.command;

import com.bit.lot.flower.auth.common.valueobject.AuthId;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SocialLoginRequestCommand {
  @NotNull
  private AuthId socialId;
  @NotNull
  private String email;
  private String phoneNumber;
  @NotNull
  private String nickname;

}
