package com.bit.lot.flower.auth.social.http.feign;

import com.bit.lot.flower.auth.social.dto.response.UserLoginResponse;
import com.bit.lot.flower.auth.social.dto.command.SocialUserLoginCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user-login",url="${service.user.domain}")
public interface LoginSocialUserFeignRequest {

  @RequestMapping(method = RequestMethod.POST, value = "/users/social")
  ResponseEntity<UserLoginResponse> login(SocialUserLoginCommand userDto);

}
