package com.bit.lot.flower.auth.common.util;

import com.bit.lot.flower.auth.common.valueobject.AuthId;

public class AuthIdCreator {

  public static AuthId getAuthIdFromString(String value) {
    return AuthId.builder().value(Long.valueOf(value)).build();
  }

  public static AuthId getAuthIdFromLong(Long value) {
    return AuthId.builder().value(value).build();
  }
}
