package com.bit.lot.flower.auth.common.dto;

import com.bit.lot.flower.auth.common.valueobject.BaseId;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RenewAccessTokenDto<T extends BaseId> {
  T authId;
  Role role;

}
