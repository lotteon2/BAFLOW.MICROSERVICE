package com.bit.lot.flower.auth.store.mapper;

import com.bit.lot.flower.auth.store.entity.StoreManagerAuth;

public class StoreManagerDataMapper {


  public static StoreManagerAuth updatedStoreManagerStatus(StoreManagerAuth storeManagerAuth,
      boolean status) {
    return StoreManagerAuth.builder().email(storeManagerAuth.getEmail())
        .id(storeManagerAuth.getId())
        .isPermitted(status).lastLogoutTime(storeManagerAuth.getLastLogoutTime())
        .password(storeManagerAuth.getPassword()).build();
  }
}
