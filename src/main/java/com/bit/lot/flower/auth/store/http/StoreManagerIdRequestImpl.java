package com.bit.lot.flower.auth.store.http;

import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.bit.lot.flower.auth.store.http.feign.StoreManagerIdFeignRequest;
import com.bit.lot.flower.auth.store.http.feign.dto.StoreLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreManagerIdRequestImpl implements StoreManagerIdRequest<AuthId> {

  private final StoreManagerIdFeignRequest storeManagerIdFeignRequest;

  @Override
  public StoreLoginResponse getId(AuthId storeManagerId) {
    return storeManagerIdFeignRequest.request(
        storeManagerId.getValue()).getBody();
  }
}
