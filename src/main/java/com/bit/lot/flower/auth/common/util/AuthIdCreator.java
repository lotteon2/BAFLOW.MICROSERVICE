package com.bit.lot.flower.auth.common.util;

import com.bit.lot.flower.auth.social.valueobject.AuthId;

public class AuthIdCreator {

  public static AuthId getAuthIdFromString(String value){
    return AuthId.builder().value(Long.valueOf(value)).build();
  }
}
