package com.bit.lot.flower.auth.store.http.message;

import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.store.valueobject.StoreId;
import org.springframework.stereotype.Component;

@Component
public interface StoreManagerStoreIdRequest<Target extends StoreId, ID extends AuthId> {
  Target requestStoreId(ID storeManagerId);
}
