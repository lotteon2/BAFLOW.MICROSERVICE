package com.bit.lot.flower.auth.social.http.feign;

import com.bit.lot.flower.auth.social.dto.message.SoftDeleteSocialUserDto;
import com.bit.lot.flower.auth.social.dto.response.LoginSuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("user-soft-delete")
public interface SoftDeleteUserFeignRequest {
  @RequestMapping(method = RequestMethod.DELETE, value = "/users")
  ResponseEntity<LoginSuccessResponse> delete(SoftDeleteSocialUserDto deleteUserDto);
}
