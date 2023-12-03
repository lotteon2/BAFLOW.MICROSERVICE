package com.bit.lot.flower.auth.store.mapper;

import com.bit.lot.flower.auth.store.dto.CreateStoreMangerCommand;
import com.bit.lot.flower.auth.store.dto.StoreManagerLoginResponseWithName;
import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpCommand;

public class StoreManagerMessageMapper {

  public static CreateStoreMangerCommand createStoreManagerCommandWithPk(StoreMangerSignUpCommand dto, Long id) {
    return CreateStoreMangerCommand.builder().businessNumberImage(dto.getBusinessNumberImage()).email(
        dto.getEmail()).name(dto.getName()).id(id).build();
  }

  public static StoreManagerLoginResponseWithName createLoginResponse(String name) {
    return StoreManagerLoginResponseWithName.builder().name(name)
        .build();
  }

}
