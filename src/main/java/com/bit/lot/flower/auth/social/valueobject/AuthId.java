package com.bit.lot.flower.auth.social.valueobject;

import com.bit.lot.flower.auth.common.valueobject.BaseId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@NoArgsConstructor
@SuperBuilder
@Getter
public class AuthId extends BaseId<Long> {

  public AuthId(Long value) {
    super(value);
  }

}
