package com.bit.lot.flower.auth.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StoreManagerLoginDto {

  private String email;
  private String password;

}
