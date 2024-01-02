package com.bit.lot.flower.auth.common.exception;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorDTO {

  private final String code;
  private final String message;
  public ErrorDTO(String code, String message) {
    this.code = code;
    this.message = message;
  }
}
