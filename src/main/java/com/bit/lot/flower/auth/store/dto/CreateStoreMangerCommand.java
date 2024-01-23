package com.bit.lot.flower.auth.store.dto;

import com.bit.lot.flower.auth.common.valueobject.BaseId;
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
  private Long id;
  @NotNull
  private String email;
  @NotNull
  private String businessNumberImage;
  @NotNull
  private String businessNumber;
  @NotNull
  private String name;

}
