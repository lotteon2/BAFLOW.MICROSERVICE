package com.bit.lot.flower.auth.social.http.controller;

import com.bit.lot.flower.auth.social.dto.response.UserFeignLoginResponse;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(value="social-auth")
public class SocialAuthRestController {

  private final OauthLogoutFacadeHelper oauthLogoutFacadeHelper;
  private final SocialAuthService<AuthId> socialAuthService;
  private final LoginSocialUserEventPublisher publisher;

  @ApiOperation(value = "유저 로그인", notes = "Authroization: Bearer 토큰 생성, Refresh토큰"
      + "Redis에 생성, HttpOnlyCookie에 생성")
  @PostMapping("/api/auth/social/login")
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

  @ApiOperation(value = "유저 로그아웃", notes = "Authroization: Bearer 토큰 제거, Refresh토큰"
      + "Redis에서 제거, HttpOnlyCookie에서 제거")
  @PostMapping("/api/auth/social/{provider}/logout")
  public ResponseEntity<String> logout(HttpServletRequest request,
      @RequestHeader AuthId socialId,
      @PathVariable AuthenticationProvider provider) {
    socialAuthService.logout(socialId);
    oauthLogoutFacadeHelper.logout(provider, request.getHeader("Authorization"));
    return ResponseEntity.ok("로그아웃이 성공했습니다.");
  }

  @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴시 로그아웃이 선행 처리가 되어야함"
      + "따라서 Authroization: Bearer 토큰 제거, Refresh토큰"
      + "Redis에서 제거, HttpOnlyCookie에서 제거 이후 인증 제공자 OAuth2 로그아웃 처리")
  @DeleteMapping("/api/auth/social")
  public ResponseEntity<String> userWithdrawalUserSelf(HttpServletRequest request,
      @PathVariable AuthenticationProvider provider,
      @RequestHeader AuthId socialId) {
    socialAuthService.userWithdrawalUserSelf(socialId);
    oauthLogoutFacadeHelper.logout(provider, request.getHeader("Authorization"));
    return ResponseEntity.ok("회원탈퇴 성공");
  }


}
