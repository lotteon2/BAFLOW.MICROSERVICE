package com.bit.lot.flower.auth.store.mapper;

import com.bit.lot.flower.auth.store.entity.StoreManagerAuth;
import com.bit.lot.flower.auth.store.valueobject.StoreManagerStatus;
import java.time.ZonedDateTime;

public class StoreManagerDataMapper {

  private StoreManagerDataMapper(){

  }
  public static StoreManagerAuth createStoreManger(String email, String password) {
    return StoreManagerAuth.builder().status(StoreManagerStatus.ROLE_STORE_MANAGER_PENDING)
        .password(password).email(email).lastLogoutTime(null).build();
  }

  public static StoreManagerAuth updatedStoreManagerStatus(StoreManagerAuth storeManagerAuth,
      StoreManagerStatus status) {
    return StoreManagerAuth.builder().email(storeManagerAuth.getEmail())
        .id(storeManagerAuth.getId()).status(status)
        .lastLogoutTime(storeManagerAuth.getLastLogoutTime())
        .password(storeManagerAuth.getPassword()).build();
  }

    public static StoreManagerAuth updatedCurrentLogOutTime(StoreManagerAuth storeManagerAuth,
      ZonedDateTime currentTime) {
    return StoreManagerAuth.builder().email(storeManagerAuth.getEmail())
        .id(storeManagerAuth.getId()).status(storeManagerAuth.getStatus())
        .lastLogoutTime(currentTime)
        .password(storeManagerAuth.getPassword()).build();
  }
}
