package com.bit.lot.flower.auth.social.exception;

import com.bit.lot.flower.auth.common.exception.AuthException;
import lombok.Getter;


@Getter
public class SocialAuthException extends AuthException {

  public SocialAuthException(String message) {
    super(message);
  }

  public SocialAuthException(String message, Throwable cause) {
    super(message, cause);
  }
}
