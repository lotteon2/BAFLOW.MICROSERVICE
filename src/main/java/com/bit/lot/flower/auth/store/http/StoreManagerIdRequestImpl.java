package com.bit.lot.flower.auth.store.http;

import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.bit.lot.flower.auth.store.http.feign.StoreManagerIdFeignRequest;
import com.bit.lot.flower.auth.store.http.feign.dto.StoreIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreManagerIdRequestImpl implements StoreManagerIdRequest<AuthId> {

  private final StoreManagerIdFeignRequest storeManagerIdFeignRequest;

  @Override
  public StoreIdResponse getId(AuthId storeManagerId) {
    return storeManagerIdFeignRequest.request(
        String.valueOf(storeManagerId.getValue())).getBody();
  }
}
