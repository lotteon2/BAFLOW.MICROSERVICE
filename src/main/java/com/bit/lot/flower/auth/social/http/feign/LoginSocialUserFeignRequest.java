package com.bit.lot.flower.auth.social.http.feign;

import bloomingblooms.response.CommonResponse;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.dto.response.UserFeignLoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "social-login-request",url="${service.user.domain}")
public interface LoginSocialUserFeignRequest{
  @PostMapping( "/client/social")
  CommonResponse<UserFeignLoginResponse> login(@RequestBody SocialLoginRequestCommand userDto);

}
