package com.bit.lot.flower.auth.system.admin.service;

import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.bit.lot.flower.auth.store.entity.StoreManagerAuth;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.bit.lot.flower.auth.store.mapper.StoreManagerDataMapper;
import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import com.bit.lot.flower.auth.store.valueobject.StoreManagerStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateStoreMangerStatusServiceImpl implements
    UpdateStoreMangerStatusService<AuthId> {

  private final StoreManagerAuthRepository storeManagerAuthRepository;

  @Override
  public void update(AuthId targetId, StoreManagerStatus status) {
    StoreManagerAuth storeManagerAuth = storeManagerAuthRepository.findById(targetId.getValue())
        .orElseThrow(() -> {
          throw new StoreManagerAuthException("존재하지 않는 스토어 매니저입니다.");
        });

    storeManagerAuthRepository.save(
        StoreManagerDataMapper.updatedStoreManagerStatus(storeManagerAuth, status));
  }
}
