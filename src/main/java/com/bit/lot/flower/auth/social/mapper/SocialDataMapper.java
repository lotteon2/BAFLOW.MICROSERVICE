package com.bit.lot.flower.auth.social.mapper;

import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.dto.message.SocialUserLoginDto;


public class SocialDataMapper {

  public static SocialUserLoginDto mapCreateSocialAuthToSocialUserDto(
      SocialLoginRequestCommand command) {
    return SocialUserLoginDto.builder().email(command.getEmail()).nickName(command.getNickname())
        .oauthId(command.getSocialId().getValue()).build();
  }

}
