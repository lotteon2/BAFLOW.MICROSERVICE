package com.bit.lot.flower.auth.system.admin.service;

import com.bit.lot.flower.auth.store.entity.StoreManagerAuth;
import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import com.bit.lot.flower.auth.store.valueobject.StoreManagerStatus;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetStoreManagerByStatusService {

  private final StoreManagerAuthRepository repository;


  public List<Long> getIdListByStatus(StoreManagerStatus storeManagerStatus, Pageable pageable) {
    List<StoreManagerAuth> storeManagerAuthList = repository.findAllByStatus(storeManagerStatus,pageable);
    return entityToIdMap(storeManagerAuthList);
  }

  private List<Long> entityToIdMap(List<StoreManagerAuth> storeManagerAuthLsit){
    return storeManagerAuthLsit.stream().map(StoreManagerAuth::getId).collect(Collectors.toList());
  }
}
