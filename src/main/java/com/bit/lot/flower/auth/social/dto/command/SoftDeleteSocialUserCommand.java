package com.bit.lot.flower.auth.social.dto.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SoftDeleteSocialUserCommand {
  private Long socialId;
}
