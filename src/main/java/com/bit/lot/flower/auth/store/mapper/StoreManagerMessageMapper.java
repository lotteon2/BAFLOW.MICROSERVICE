package com.bit.lot.flower.auth.store.mapper;

import com.bit.lot.flower.auth.store.dto.CreateStoreMangerCommand;
import com.bit.lot.flower.auth.store.dto.StoreManagerLoginResponseWithNameAndStoreId;
import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpCommand;
import com.bit.lot.flower.auth.store.valueobject.StoreId;

public class StoreManagerMessageMapper {

  public static CreateStoreMangerCommand createStoreManagerCommandWithPk(StoreMangerSignUpCommand dto, Long id) {
    return CreateStoreMangerCommand.builder().businessNumberImage(dto.getBusinessNumberImage()).email(
        dto.getEmail()).name(dto.getName()).id(id).build();
  }

  public static StoreManagerLoginResponseWithNameAndStoreId createLoginResponse(String name,
      Long storeId) {
    return StoreManagerLoginResponseWithNameAndStoreId.builder().name(name).storeId(storeId)
        .build();
  }

}
