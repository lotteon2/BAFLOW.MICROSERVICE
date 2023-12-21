package com.bit.lot.flower.auth.store.http.feign;

import bloomingblooms.response.CommonResponse;
import com.bit.lot.flower.auth.store.dto.CreateStoreMangerCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "create-store-manager-request", url = "${service.user.domain}")
public interface CreateStoreManagerFeignRequest {
  @PostMapping("/store-manager")
  CommonResponse<String> create(CreateStoreMangerCommand createStoreMangerCommand);
}
