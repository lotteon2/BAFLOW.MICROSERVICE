package com.bit.lot.flower.auth.store.dto.response;

import com.bit.lot.flower.auth.store.dto.response.StoreManagerLoginResponse;
import com.bit.lot.flower.auth.store.valueobject.StoreId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StoreManagerLoginResponseWithStoreIdAndName extends
    StoreManagerLoginResponse {
  private StoreId storeId;
}
