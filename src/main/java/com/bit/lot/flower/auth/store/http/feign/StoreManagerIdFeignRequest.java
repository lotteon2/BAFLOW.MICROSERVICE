package com.bit.lot.flower.auth.store.http.feign;

import com.bit.lot.flower.auth.store.http.feign.dto.StoreLoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "get-store-manager-id" , url = "${service.user.domain}")
public interface StoreManagerIdFeignRequest {
  @GetMapping( "/client/users/store-manager/{storeManagerId}")
  ResponseEntity<StoreLoginResponse> request(@PathVariable Long storeManagerId);

}
