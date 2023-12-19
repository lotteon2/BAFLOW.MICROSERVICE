package com.bit.lot.flower.auth.store.http.feign;

import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.store.valueobject.StoreId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "get-store-id", url = "${service.store.domain}")
public interface StoreIdFeignRequest{
  @GetMapping("/stores/id")
  public ResponseEntity<StoreId> request(@RequestHeader Long storeUserId);
}
