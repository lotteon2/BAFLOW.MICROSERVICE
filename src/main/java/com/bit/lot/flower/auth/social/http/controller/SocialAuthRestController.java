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
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SocialAuthRestController {

  private final UserWithdrawalRequest<UserId> socialWithdrawalRequest;
  private final OauthLogoutFacadeHelper oauthLogoutFacadeHelper;
  private final SocialAuthService<AuthId> socialAuthService;
  private final LoginSocialUserRequest userDataRequest;

  @PostMapping("/social/login")
  public CommonResponse<UserFeignLoginResponse> loginWithUserServiceResponse(HttpServletRequest request) {
    SocialLoginRequestCommand commandFromAuthenticationFilter = (SocialLoginRequestCommand)request.getAttribute("command");
    UserFeignLoginResponse userFeignLoginResponse = userDataRequest.request(commandFromAuthenticationFilter);
    return CommonResponse.success(userFeignLoginResponse);
  }


  @PostMapping("/social/{provider}/logout")
  public CommonResponse<String> logout(
      @AuthenticationPrincipal AuthId socialId,
      @PathVariable AuthenticationProvider provider) {
    socialAuthService.logout(socialId);
    oauthLogoutFacadeHelper.logout(provider);
    return CommonResponse.success("로그아웃이 성공했습니다.");
  }


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
