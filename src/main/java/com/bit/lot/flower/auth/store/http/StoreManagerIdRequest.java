package com.bit.lot.flower.auth.store.http;

import com.bit.lot.flower.auth.common.valueobject.BaseId;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.bit.lot.flower.auth.store.http.feign.dto.StoreIdResponse;
import org.springframework.stereotype.Service;

@Service
public interface StoreManagerIdRequest<ID extends BaseId> {
  public StoreIdResponse getId(AuthId storeManagerId);
}
