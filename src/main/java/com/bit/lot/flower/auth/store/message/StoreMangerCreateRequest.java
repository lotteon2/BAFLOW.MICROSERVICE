package com.bit.lot.flower.auth.store.message;

import com.bit.lot.flower.auth.store.dto.CreateStoreMangerCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

@Component
public interface StoreMangerCreateRequest {
  public void publish(CreateStoreMangerCommand dto) throws JsonProcessingException;
}
