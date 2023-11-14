package com.bit.lot.flower.auth.social.dto.command;

import javax.ws.rs.GET;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SocialLoginRequestCommand {
  private Long socialId;
  private String email;
  private String nickname;
}
