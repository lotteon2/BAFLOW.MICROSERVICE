package com.bit.lot.flower.auth.social.http.controller;

import com.bit.lot.flower.auth.social.dto.UserFeignLoginResponse;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.dto.message.SocialUserLoginDto;
import com.bit.lot.flower.auth.social.dto.response.LoginSuccessResponse;
import com.bit.lot.flower.auth.social.http.helper.OauthLogoutFacadeHelper;
import com.bit.lot.flower.auth.social.http.valueobject.UserId;
import com.bit.lot.flower.auth.social.mapper.SocialDataMapper;
import com.bit.lot.flower.auth.social.message.LoginSocialUserEventPublisher;
import com.bit.lot.flower.auth.social.service.SocialAuthService;
import com.bit.lot.flower.auth.social.valueobject.AuthenticationProvider;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/auth/social")
@RequiredArgsConstructor
public class SocialAuthController {

  private final OauthLogoutFacadeHelper oauthLogoutFacadeHelper;
  private final SocialAuthService<AuthId> socialAuthService;
  private final LoginSocialUserEventPublisher publisher;

  @PostMapping("/login")
  public ResponseEntity<LoginSuccessResponse> loginWithUserServiceResponse(
      HttpServletRequest request) {
    SocialLoginRequestCommand command = (SocialLoginRequestCommand) request.getAttribute(
        "loginDto");
    SocialUserLoginDto userDto = SocialDataMapper.mapCreateSocialAuthToSocialUserDto(command);

    socialAuthService.login(command.getSocialId());

    UserFeignLoginResponse<UserId> userFeignLoginResponse = publisher.publish(userDto);
    request.setAttribute("userId", userFeignLoginResponse.getUserId());

    return ResponseEntity.ok(
        new LoginSuccessResponse(userFeignLoginResponse.isPhoneNumberIsRegistered()));
  }

  @PostMapping("/{provider}/logout")
  public ResponseEntity<String> logout(HttpServletRequest request,
      @RequestHeader AuthId socialId,
      @PathVariable AuthenticationProvider provider) {
    socialAuthService.logout(socialId);
    oauthLogoutFacadeHelper.logout(provider, request.getHeader("Authorization"));
    return ResponseEntity.ok("로그아웃이 성공했습니다.");
  }

  @DeleteMapping
  public ResponseEntity<String> userWithdrawalUserSelf(HttpServletRequest request,
      @PathVariable AuthenticationProvider provider,
      @RequestHeader AuthId socialId) {
    socialAuthService.userWithdrawalUserSelf(socialId);
    oauthLogoutFacadeHelper.logout(provider, request.getHeader("Authorization"));
    return ResponseEntity.ok("회원탈퇴 성공");
  }


}
