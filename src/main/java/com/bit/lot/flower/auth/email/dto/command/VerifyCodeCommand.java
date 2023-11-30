package com.bit.lot.flower.auth.email.dto.command;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VerifyCodeCommand {

  @NotNull
  private String code;
}
