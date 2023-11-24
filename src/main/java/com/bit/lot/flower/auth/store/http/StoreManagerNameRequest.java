package com.bit.lot.flower.auth.store.http;

import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.bit.lot.flower.auth.store.http.feign.dto.StoreManagerNameDto;
import org.springframework.stereotype.Service;

@Service
public interface StoreManagerNameRequest<ID extends AuthId> {
  public StoreManagerNameDto getName(ID storeManagerAuthId);
}
