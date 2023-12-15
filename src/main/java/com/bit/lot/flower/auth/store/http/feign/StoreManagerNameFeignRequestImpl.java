package com.bit.lot.flower.auth.store.http.feign;

import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.store.http.feign.dto.StoreManagerNameDto;
import com.bit.lot.flower.auth.store.http.message.StoreManagerNameRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreManagerNameFeignRequestImpl implements StoreManagerNameRequest<AuthId> {

  private final StoreManagerNameFeignRequest storeManagerFeignRequest;
  @Override
  public StoreManagerNameDto getName(AuthId storeManagerAuthId) {
    return storeManagerFeignRequest.request(storeManagerAuthId.getValue()).getBody();
  }
}
