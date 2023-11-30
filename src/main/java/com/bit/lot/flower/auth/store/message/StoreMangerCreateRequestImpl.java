package com.bit.lot.flower.auth.store.message;

import com.bit.lot.flower.auth.store.dto.CreateStoreMangerCommand;
import com.bit.lot.flower.auth.store.http.feign.CreateStoreManagerFeignRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class StoreMangerCreateRequestImpl implements
    StoreMangerCreateRequest {

  private final CreateStoreManagerFeignRequest feignRequest;
  @Override
  public void publish(CreateStoreMangerCommand dto) {
    feignRequest.create(dto);
  }
}
