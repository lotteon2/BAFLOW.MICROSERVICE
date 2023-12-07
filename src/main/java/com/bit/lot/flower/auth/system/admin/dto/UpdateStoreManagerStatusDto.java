package com.bit.lot.flower.auth.system.admin.dto;

import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.store.valueobject.StoreManagerStatus;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class
UpdateStoreManagerStatusDto {

  @NotNull
  private StoreManagerStatus status;
  @NotNull
  private AuthId storeManagerId;
}
