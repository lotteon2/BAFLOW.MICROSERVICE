package com.bit.lot.flower.auth.system.admin.dto.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SystemAdminLoginDto {

  private  String email;
  private  String password;
}
