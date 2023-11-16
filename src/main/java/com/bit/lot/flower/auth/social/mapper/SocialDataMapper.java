package com.bit.lot.flower.auth.social.mapper;

import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.dto.message.SocialUserCreateDto;


public class SocialDataMapper {

  public static SocialUserCreateDto mapCreateSocialAuthToSocialUserDto(
      SocialLoginRequestCommand command) {
    return SocialUserCreateDto.builder().email(command.getEmail()).nickName(command.getNickname())
        .oauthId(command.getSocialId()).build();
  }

}
