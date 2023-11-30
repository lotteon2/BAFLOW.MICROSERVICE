package com.bit.lot.flower.auth.social.dto.message;

import com.bit.lot.flower.auth.social.valueobject.AuthId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SocialUserLoginDto {
  private AuthId socialId;
  private String nickname;
  private String email;
}
