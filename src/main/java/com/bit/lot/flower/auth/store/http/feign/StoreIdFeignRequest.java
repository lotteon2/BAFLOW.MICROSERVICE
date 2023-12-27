package com.bit.lot.flower.auth.store.http.feign;

import bloomingblooms.response.CommonResponse;
import com.bit.lot.flower.auth.store.valueobject.StoreId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "get-store-id", url = "${service.store.domain}")
public interface StoreIdFeignRequest{
  @GetMapping("/client/stores/id")
  public CommonResponse<StoreId> request(@RequestHeader Long userId);
}
