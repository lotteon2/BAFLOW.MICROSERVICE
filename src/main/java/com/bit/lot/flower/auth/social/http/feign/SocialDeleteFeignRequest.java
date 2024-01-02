package com.bit.lot.flower.auth.social.http.feign;

import bloomingblooms.response.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "social-user-delete", url = "${service.user.domain}")
public interface SocialDeleteFeignRequest {

  @PutMapping("/client/users/{userId}")
  CommonResponse<Boolean> delete(@PathVariable Long userId);

}
