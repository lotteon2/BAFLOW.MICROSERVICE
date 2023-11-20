package com.bit.lot.flower.auth.system.admin.service;

import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.bit.lot.flower.auth.store.valueobject.StoreManagerStatus;
import org.springframework.stereotype.Service;

@Service
public interface UpdateStoreMangerStatusService<ID extends AuthId> {

  public void update(ID targetId, StoreManagerStatus status);
}
