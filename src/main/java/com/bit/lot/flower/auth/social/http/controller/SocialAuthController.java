package com.bit.lot.flower.auth.social.http.controller;

import com.bit.lot.flower.auth.social.dto.command.SocialLogOutRequestCommand;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.dto.message.SocialUserDto;
import com.bit.lot.flower.auth.social.dto.response.LoginSuccessResponse;
import com.bit.lot.flower.auth.social.http.helper.OauthLoginFacadeHelper;
import com.bit.lot.flower.auth.social.http.helper.OauthLogoutFacadeHelper;
import com.bit.lot.flower.auth.social.mapper.SocialDataMapper;
import com.bit.lot.flower.auth.social.message.LoginSocialUserEventPublisher;
import com.bit.lot.flower.auth.social.service.SocialAuthService;
import com.bit.lot.flower.auth.social.valueobject.AuthenticationProvider;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/auth")
@RequiredArgsConstructor
public class SocialAuthController {

  private final OauthLogoutFacadeHelper oauthLogoutFacadeHelper;
  private final SocialAuthService socialAuthService;
  private final LoginSocialUserEventPublisher publisher;

  @PostMapping("/auth/social/login")
  public ResponseEntity<LoginSuccessResponse> login(HttpServletRequest request,
      @RequestHeader @NotNull SocialLoginRequestCommand command) {
    SocialUserDto userDto = SocialDataMapper.mapCreateSocialAuthToSocialUserDto(command);
    socialAuthService.login(Long.valueOf(command.getSocialId()));
    LoginSuccessResponse response = publisher.publish(userDto);
    request.setAttribute("userId", response.getUserId());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/auth/social/{provider}/logout")
  public ResponseEntity<String> logout(HttpServletRequest request,
      @RequestBody @Valid SocialLogOutRequestCommand command,
      @PathVariable AuthenticationProvider provider) {
    socialAuthService.logout(command.getSocialId());
    oauthLogoutFacadeHelper.logout(provider, request.getHeader("Authorization"));
    return ResponseEntity.ok("로그아웃이 성공했습니다.");
  }

}
