package com.bit.lot.flower.auth.store.exception;

import com.bit.lot.flower.auth.common.exception.AuthException;

public class StoreManagerAuthException extends
    AuthException {

  public StoreManagerAuthException(String message, Throwable cause) {
    super(message, cause);
  }


  public StoreManagerAuthException(String message) {
    super(message);
  }
}
