package com.bit.lot.flower.auth.social.http.feign;

import com.bit.lot.flower.auth.social.dto.response.UserFeignLoginResponse;
import com.bit.lot.flower.auth.social.dto.message.SocialUserLoginDto;
import com.bit.lot.flower.auth.social.http.valueobject.UserId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("user-login")
public interface LoginSocialUserFeignRequest {

  @RequestMapping(method = RequestMethod.POST, value = "/users")
  ResponseEntity<UserFeignLoginResponse> login(SocialUserLoginDto userDto);

}
