package com.bit.lot.flower.auth.store.mapper;

import com.bit.lot.flower.auth.store.dto.CreateStoreMangerDto;
import com.bit.lot.flower.auth.store.dto.response.StoreManagerLoginResponseWithStoreIdAndName;
import com.bit.lot.flower.auth.store.dto.command.StoreMangerSignUpCommand;
import com.bit.lot.flower.auth.store.valueobject.StoreId;

public class StoreManagerMessageMapper {

  public static CreateStoreMangerDto createStoreManagerMessage(StoreMangerSignUpCommand dto) {
    return CreateStoreMangerDto.builder().businessNumberImage(dto.getBusinessNumberImage()).email(
        dto.getEmail()).name(dto.getName()).build();
  }

  public static StoreManagerLoginResponseWithStoreIdAndName createLoginResponse(StoreId storeId,
      String name) {
    return StoreManagerLoginResponseWithStoreIdAndName.builder().storeId(storeId).name(name)
        .build();
  }

}
