package com.bit.lot.flower.auth.social.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public class SocialAuthException extends RuntimeException{

  public SocialAuthException(String message) {
    super(message);
  }

  public SocialAuthException(String message, Throwable cause) {
    super(message, cause);
  }
}
