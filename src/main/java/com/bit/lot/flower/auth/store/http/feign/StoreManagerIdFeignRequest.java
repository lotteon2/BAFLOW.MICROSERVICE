package com.bit.lot.flower.auth.store.http.feign;

import com.bit.lot.flower.auth.store.http.feign.dto.StoreLoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "get-store-manager-id" , url = "${service.user.domain}")
public interface StoreManagerIdFeignRequest {
  @RequestMapping(method = RequestMethod.GET, value = "/users/store-manager/{storeManagerId}")
  ResponseEntity<StoreLoginResponse> request(@PathVariable Long storeManagerId);

}
