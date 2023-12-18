package com.bit.lot.flower.auth.store.http.feign;

import com.bit.lot.flower.auth.store.http.feign.dto.StoreManagerNameDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "store-manager-name-request", url = "${service.store.domain}")
public interface StoreManagerNameFeignRequest {
  @GetMapping(value = "/stores/{storeManagerId}")
  ResponseEntity<StoreManagerNameDto> request(@PathVariable Long storeManagerId);
}
