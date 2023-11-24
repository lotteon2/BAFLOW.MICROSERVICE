package com.bit.lot.flower.auth.store.http.feign;

import com.bit.lot.flower.auth.social.dto.SoftDeleteSocialUserDto;
import com.bit.lot.flower.auth.social.dto.response.LoginSuccessResponse;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.bit.lot.flower.auth.store.http.feign.dto.StoreManagerNameDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("get-store-manager-name")
public interface StoreManagerNameFeignRequest {
  @RequestMapping(method = RequestMethod.GET, value = "/users/store-manager/{storeManagerId}")
  ResponseEntity<StoreManagerNameDto> request(AuthId storeManagerId);
}
