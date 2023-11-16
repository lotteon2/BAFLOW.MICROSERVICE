package com.bit.lot.flower.auth.social.http.controller;

import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.dto.message.SocialUserCreateDto;
import com.bit.lot.flower.auth.social.dto.response.LoginSuccessResponse;
import com.bit.lot.flower.auth.social.http.helper.OauthLogoutFacadeHelper;
import com.bit.lot.flower.auth.social.mapper.SocialDataMapper;
import com.bit.lot.flower.auth.social.message.LoginSocialUserEventPublisher;
import com.bit.lot.flower.auth.social.service.SocialAuthService;
import com.bit.lot.flower.auth.social.valueobject.AuthenticationProvider;
import com.bit.lot.flower.auth.social.valueobject.SocialAuthId;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/auth/social")
@RequiredArgsConstructor
public class SocialAuthController {

  private final OauthLogoutFacadeHelper oauthLogoutFacadeHelper;
  private final SocialAuthService<SocialAuthId> socialAuthService;
  private final LoginSocialUserEventPublisher publisher;

  @PostMapping("/login")
  public ResponseEntity<LoginSuccessResponse> loginWithUserServiceResponse(
      HttpServletRequest request,
      @RequestHeader @NotNull SocialLoginRequestCommand command) {
    SocialUserCreateDto userDto = SocialDataMapper.mapCreateSocialAuthToSocialUserDto(command);
    socialAuthService.login(command.getSocialId());
    LoginSuccessResponse response = publisher.publish(userDto);
    request.setAttribute("userId", response.getUserId());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/{provider}/logout")
  public ResponseEntity<String> logout(HttpServletRequest request,
      @RequestHeader SocialAuthId socialId,
      @PathVariable AuthenticationProvider provider) {
    socialAuthService.logout(socialId);
    oauthLogoutFacadeHelper.logout(provider, request.getHeader("Authorization"));
    return ResponseEntity.ok("로그아웃이 성공했습니다.");
  }

  @DeleteMapping
  public ResponseEntity<String> userWithdrawalUserSelf(HttpServletRequest request,
      @PathVariable AuthenticationProvider provider,
      @RequestHeader SocialAuthId socialId) {
    socialAuthService.userWithdrawalUserSelf(socialId);
    oauthLogoutFacadeHelper.logout(provider, request.getHeader("Authorization"));
    return ResponseEntity.ok("회원탈퇴 성공");
  }

}
