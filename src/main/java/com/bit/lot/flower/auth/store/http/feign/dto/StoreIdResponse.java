package com.bit.lot.flower.auth.store.http.feign.dto;

import com.bit.lot.flower.auth.store.valueobject.StoreId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StoreIdResponse {
  StoreId storeId;
}
