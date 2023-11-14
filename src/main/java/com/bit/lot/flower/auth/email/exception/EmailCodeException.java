package com.bit.lot.flower.auth.email.exception;

import com.bit.lot.flower.auth.email.entity.EmailCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class EmailCodeException extends RuntimeException {

  public EmailCodeException(String input){
    super(input);
  }

}
