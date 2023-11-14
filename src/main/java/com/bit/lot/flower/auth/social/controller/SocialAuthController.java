package com.bit.lot.flower.auth.social.controller;

import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.dto.message.SocialUserDto;
import com.bit.lot.flower.auth.social.dto.response.LoginSuccessResponse;
import com.bit.lot.flower.auth.social.mapper.SocialDataMapper;
import com.bit.lot.flower.auth.social.message.LoginSocialUserEventPublisher;
import com.bit.lot.flower.auth.social.service.SocialAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SocialAuthController {

  private final SocialAuthService socialAuthService;
  private final LoginSocialUserEventPublisher publisher;

  @PostMapping("/auth/social/login")
  public ResponseEntity<LoginSuccessResponse> login(SocialLoginRequestCommand command) {
    SocialUserDto userDto = SocialDataMapper.mapCreateSocialAuthToSocialUserDto(command);
    Long id = command.getSocialId();
    socialAuthService.login(id);
    return ResponseEntity.ok(publisher.publish(userDto));
  }

  @PostMapping("/auth/social/logout")
  public ResponseEntity<LoginSuccessResponse> logout(SocialLoginRequestCommand command) {

  }

}
