package com.bit.lot.flower.auth.store.message;

import com.bit.lot.flower.auth.store.dto.CreateStoreMangerDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public interface StoreMangerCreateRequest {
  public void publish(CreateStoreMangerDto dto);
}
