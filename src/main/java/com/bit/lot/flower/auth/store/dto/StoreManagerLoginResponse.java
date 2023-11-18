package com.bit.lot.flower.auth.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StoreManagerLoginResponse {

  private final String message ="로그인 완료";
  private String name ;
}
