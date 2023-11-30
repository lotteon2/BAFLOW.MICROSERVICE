package com.bit.lot.flower.auth.store.message;

import com.bit.lot.flower.auth.store.dto.CreateStoreMangerCommand;
import org.springframework.stereotype.Component;

@Component
public interface StoreMangerCreateRequest {
  public void publish(CreateStoreMangerCommand dto);
}
