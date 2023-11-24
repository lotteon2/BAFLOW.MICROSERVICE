package com.bit.lot.flower.auth.store.http.feign;

import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.bit.lot.flower.auth.store.http.feign.dto.StoreIdResponse;
import com.bit.lot.flower.auth.store.http.feign.dto.StoreManagerNameDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("get-store-manager-id")
public interface StoreManagerIdFeignRequest {
  @RequestMapping(method = RequestMethod.GET, value = "/stores/id")
  ResponseEntity<StoreIdResponse> request(@RequestHeader String authId);

}
