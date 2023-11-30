package com.bit.lot.flower.auth.store.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StoreManagerLoginCommand {

  private String email;
  private String password;

}
