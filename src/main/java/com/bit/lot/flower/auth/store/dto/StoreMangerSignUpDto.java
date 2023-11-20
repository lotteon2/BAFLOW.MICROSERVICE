package com.bit.lot.flower.auth.store.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StoreMangerSignUpDto {

  @NotNull
  private String email;
  @NotNull
  private String name;
  @NotNull
  private boolean isEmailVerified;
  @NotNull
  private String password;
  @NotNull
  private String businessNumberImage;
}
