package com.bit.lot.flower.auth.store.http.feign;

import com.bit.lot.flower.auth.social.dto.UserFeignLoginResponse;
import com.bit.lot.flower.auth.social.dto.message.SocialUserLoginDto;
import com.bit.lot.flower.auth.social.http.valueobject.UserId;
import com.bit.lot.flower.auth.store.dto.CreateStoreMangerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("create-store-manager")
public interface CreateStoreManagerFeignRequest {
  @RequestMapping(method = RequestMethod.POST, value = "/users/store-manager")
  ResponseEntity<String> create(CreateStoreMangerDto createStoreMangerDto);
}
