package com.bit.lot.flower.auth.social.http.feign;

import com.bit.lot.flower.auth.social.dto.command.SoftDeleteSocialUserCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user-soft-delete", url = "${service.user.domain}")
public interface SoftDeleteUserFeignRequest {
  @RequestMapping(method = RequestMethod.DELETE, value = "/users")
  ResponseEntity<Boolean> delete(SoftDeleteSocialUserCommand deleteUserDto);
}
