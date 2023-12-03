package com.bit.lot.flower.auth.store.message;

import com.bit.lot.flower.auth.common.valueobject.BaseId;
import com.bit.lot.flower.auth.store.dto.CreateStoreMangerCommand;
import com.bit.lot.flower.auth.store.http.feign.CreateStoreManagerFeignRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class StoreMangerCreateRequestImpl<ID extends BaseId> implements
    StoreMangerCreateRequest<ID> {

  private final CreateStoreManagerFeignRequest<ID> feignRequest;
  @Override
  public void publish(CreateStoreMangerCommand<ID> dto) {
    feignRequest.create(dto);
  }
}
