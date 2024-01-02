package com.bit.lot.flower.auth.common.dto;

import com.bit.lot.flower.auth.common.valueobject.BaseId;
import com.bit.lot.flower.auth.common.valueobject.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RenewAccessTokenDto<T extends BaseId> {
  private T id;
  private Role role;
  private String expiredAccessToken;

}
