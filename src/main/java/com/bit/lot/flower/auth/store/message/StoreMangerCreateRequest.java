package com.bit.lot.flower.auth.store.message;

import com.bit.lot.flower.auth.common.valueobject.BaseId;
import com.bit.lot.flower.auth.store.dto.CreateStoreMangerCommand;
import org.springframework.stereotype.Component;

@Component
public interface StoreMangerCreateRequest<ID extends BaseId> {
  public void publish(CreateStoreMangerCommand<ID> dto);
}
