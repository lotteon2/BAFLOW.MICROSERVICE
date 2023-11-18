package com.bit.lot.flower.auth.social.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SocialUserLoginDto {
  private Long oauthId;
  private String nickName;
  private String email;
}
