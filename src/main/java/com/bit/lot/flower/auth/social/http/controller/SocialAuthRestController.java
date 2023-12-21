package com.bit.lot.flower.auth.social.http.controller;

import bloomingblooms.response.CommonResponse;
import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.common.valueobject.AuthenticationProvider;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.dto.response.UserFeignLoginResponse;
import com.bit.lot.flower.auth.social.http.feign.UserWithdrawalRequest;
import com.bit.lot.flower.auth.social.http.helper.OauthLogoutFacadeHelper;
import com.bit.lot.flower.auth.social.http.valueobject.UserId;
import com.bit.lot.flower.auth.social.message.LoginSocialUserRequest;
import com.bit.lot.flower.auth.social.service.SocialAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(value="social-auth")
public class SocialAuthRestController {

  private final UserWithdrawalRequest<UserId> socialWithdrawalRequest;
  private final OauthLogoutFacadeHelper oauthLogoutFacadeHelper;
  private final SocialAuthService<AuthId> socialAuthService;
  private final LoginSocialUserRequest userDataRequest;

  @ApiOperation(value = "유저 로그인", notes = "Authroization: Bearer 토큰 생성, Refresh토큰"
      + "Redis에 생성, HttpOnlyCookie에 생성")
  @PostMapping("/social/login")
  public CommonResponse<UserFeignLoginResponse> loginWithUserServiceResponse(HttpServletRequest request) {
    SocialLoginRequestCommand commandFromAuthenticationFilter = (SocialLoginRequestCommand)request.getAttribute("command");
    UserFeignLoginResponse userFeignLoginResponse = userDataRequest.request(commandFromAuthenticationFilter);
    return CommonResponse.success(userFeignLoginResponse);
  }

  @ApiOperation(value = "유저 로그아웃", notes = "Authroization: Bearer 토큰 제거, Refresh토큰"
      + "Redis에서 제거, HttpOnlyCookie에서 제거")
  @PostMapping("/social/{provider}/logout")
  public CommonResponse<String> logout(
      @AuthenticationPrincipal AuthId socialId,
      @PathVariable AuthenticationProvider provider) {
    socialAuthService.logout(socialId);
    oauthLogoutFacadeHelper.logout(provider);
    return CommonResponse.success("로그아웃이 성공했습니다.");
  }

  @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴시 로그아웃이 선행 처리가 되어야함"
      + "따라서 Authroization: Bearer 토큰 제거, Refresh토큰"
      + "Redis에서 제거, HttpOnlyCookie에서 제거 이후 인증 제공자 OAuth2 로그아웃 처리")
  @DeleteMapping("/social/{provider}")
  public CommonResponse<String> userWithdrawalUserSelf(
      @PathVariable AuthenticationProvider provider,
      @AuthenticationPrincipal AuthId socialId) {
    oauthLogoutFacadeHelper.logout(provider);
    socialAuthService.logout(socialId);
    socialAuthService.userWithdrawalUserSelf(socialId);
    socialWithdrawalRequest.request(new UserId(socialId.getValue()));
    return CommonResponse.success("회원탈퇴 성공");
  }


}
