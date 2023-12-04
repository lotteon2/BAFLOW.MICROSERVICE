package com.bit.lot.flower.auth.system.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SystemAdminLoginDto {
  private  Long id;

  private  String password;
}
