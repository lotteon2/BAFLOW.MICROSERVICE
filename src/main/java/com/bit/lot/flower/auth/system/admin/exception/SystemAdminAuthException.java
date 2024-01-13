package com.bit.lot.flower.auth.system.admin.exception;

import com.bit.lot.flower.auth.common.exception.AuthException;

public class SystemAdminAuthException extends
    AuthException {

  public SystemAdminAuthException(String message) {
    super(message);
  }

  public SystemAdminAuthException(String message, Throwable cause) {
    super(message, cause);
  }
}
