package com.bit.lot.flower.auth.store.http.feign;

import bloomingblooms.response.CommonResponse;
import com.bit.lot.flower.auth.store.http.feign.dto.StoreManagerNameDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "store-manager-name-request", url = "${service.user.domain}")
public interface StoreManagerNameFeignRequest {
  @GetMapping(value = "/client/stores/{storeManagerId}")
  CommonResponse<StoreManagerNameDto> request(@PathVariable Long storeManagerId);
}
