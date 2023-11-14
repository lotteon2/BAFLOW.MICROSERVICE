package com.bit.lot.flower.auth.social.http.feign;

import com.bit.lot.flower.auth.social.dto.message.SocialUserDto;
import com.bit.lot.flower.auth.social.dto.response.LoginSuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("users")
public interface LoginSocialUserFeignRequest {

  @RequestMapping(method = RequestMethod.POST, value = "/users")
  ResponseEntity<LoginSuccessResponse> login(SocialUserDto userDto);

}
