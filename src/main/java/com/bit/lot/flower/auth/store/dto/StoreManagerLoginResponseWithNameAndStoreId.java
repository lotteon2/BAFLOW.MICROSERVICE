package com.bit.lot.flower.auth.store.dto;

import com.bit.lot.flower.auth.store.valueobject.StoreId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StoreManagerLoginResponseWithNameAndStoreId extends
    StoreManagerLoginResponse {
  private Long storeId;
}
