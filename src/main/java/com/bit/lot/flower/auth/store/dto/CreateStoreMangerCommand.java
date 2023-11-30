package com.bit.lot.flower.auth.store.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateStoreMangerCommand {
  @NotNull
  private String email;
  @NotNull
  private String businessNumberImage;
  @NotNull
  private String name;

}
