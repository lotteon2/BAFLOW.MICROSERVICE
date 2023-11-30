package com.bit.lot.flower.auth.store.mapper;

import com.bit.lot.flower.auth.store.dto.CreateStoreMangerCommand;
import com.bit.lot.flower.auth.store.dto.StoreManagerLoginResponseWithStoreIdAndName;
import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpCommand;
import com.bit.lot.flower.auth.store.valueobject.StoreId;

public class StoreManagerMessageMapper {

  public static CreateStoreMangerCommand createStoreManagerMessage(StoreMangerSignUpCommand dto) {
    return CreateStoreMangerCommand.builder().businessNumberImage(dto.getBusinessNumberImage()).email(
        dto.getEmail()).name(dto.getName()).build();
  }

  public static StoreManagerLoginResponseWithStoreIdAndName createLoginResponse(StoreId storeId,
      String name) {
    return StoreManagerLoginResponseWithStoreIdAndName.builder().storeId(storeId).name(name)
        .build();
  }

}
