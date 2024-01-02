package com.bit.lot.flower.auth.social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Oauth2LoginDto {

  private String id;
  private String email;
  private String nickname;

}
