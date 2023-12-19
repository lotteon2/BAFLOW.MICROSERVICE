package com.bit.lot.flower.auth.store.http.message;

import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.store.http.feign.StoreIdFeignRequest;
import com.bit.lot.flower.auth.store.valueobject.StoreId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StoreManagerStoreIdRequestImpl implements
    StoreManagerStoreIdRequest<StoreId, AuthId> {

  private final StoreIdFeignRequest feignRequest;
  @Override
  public StoreId requestStoreId(AuthId storeManagerId) {
    return feignRequest.request(storeManagerId.getValue()).getBody();
  }
}
