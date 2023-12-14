package com.bit.lot.flower.auth.store.http.feign;

import com.bit.lot.flower.auth.store.dto.CreateStoreMangerCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "create-store-manager-request", url = "${service.user.domain}")
public interface CreateStoreManagerFeignRequest {
  @RequestMapping(method = RequestMethod.POST, value = "/store-manager")
  ResponseEntity<String> create(CreateStoreMangerCommand createStoreMangerCommand);
}
