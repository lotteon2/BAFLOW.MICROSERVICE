package com.bit.lot.flower.auth.email.dto.command;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmailCodeCommand {
  @NotNull
  private String email;
}
