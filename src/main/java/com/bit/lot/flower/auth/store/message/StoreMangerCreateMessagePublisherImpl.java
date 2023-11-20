package com.bit.lot.flower.auth.store.message;

import com.bit.lot.flower.auth.store.dto.CreateStoreMangerDto;
import com.bit.lot.flower.auth.store.http.feign.CreateStoreManagerFeignRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class StoreMangerCreateMessagePublisherImpl implements
    StoreMangerCreateMessagePublisher {

  private final CreateStoreManagerFeignRequest feignRequest;
  @Override
  public void publish(CreateStoreMangerDto dto) {
    feignRequest.create(dto);
  }
}
