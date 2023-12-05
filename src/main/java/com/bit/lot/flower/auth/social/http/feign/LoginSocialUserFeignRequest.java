package com.bit.lot.flower.auth.social.http.feign;

import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.dto.response.UserFeignLoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "social-login-request",url="${service.user.domain}")
public interface LoginSocialUserFeignRequest{
  @RequestMapping(method = RequestMethod.POST, value = "/users/social")
  ResponseEntity<UserFeignLoginResponse> login(@RequestBody SocialLoginRequestCommand userDto);

}
