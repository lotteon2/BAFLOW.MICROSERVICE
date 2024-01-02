package com.bit.lot.flower.auth.store.valueobject;

import com.bit.lot.flower.auth.common.valueobject.BaseId;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
public class StoreId  extends BaseId<Long> {
  public StoreId(Long value) {
    super(value);
  }
}
