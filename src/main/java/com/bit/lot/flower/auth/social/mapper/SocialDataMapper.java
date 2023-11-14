package com.bit.lot.flower.auth.social.mapper;

import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.dto.message.SocialUserDto;
import org.springframework.stereotype.Component;


public class SocialDataMapper {

  public static SocialUserDto mapCreateSocialAuthToSocialUserDto(
      SocialLoginRequestCommand command) {
    return SocialUserDto.builder().email(command.getEmail()).nickName(command.getNickname())
        .oauthId(command.getSocialId()).build();
  }

}
