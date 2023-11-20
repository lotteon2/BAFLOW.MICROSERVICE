package com.bit.lot.flower.auth.store.mapper;

import com.bit.lot.flower.auth.store.dto.CreateStoreMangerDto;
import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpDto;

public class StoreManagerMessageMapper {

  public static  CreateStoreMangerDto createStoreManagerMessage(StoreMangerSignUpDto dto){
    return CreateStoreMangerDto.builder().businessNumberImage(dto.getBusinessNumberImage()).email(
        dto.getEmail()).name(dto.getName()).build();
  }

}
