package com.bit.lot.flower.auth.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public abstract class StoreManagerLoginResponse {

  private final String message ="로그인 완료";
  private String name ;
}
