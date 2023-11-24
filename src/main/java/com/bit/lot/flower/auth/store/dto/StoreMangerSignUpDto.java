package com.bit.lot.flower.auth.store.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StoreMangerSignUpDto {

  @Email
  @NotNull
  private String email;
  @Length(min = 1, max = 10)
  @NotNull
  private String name;
  @NotNull
  private boolean isEmailVerified;
  @NotNull
  private String password;
  @URL
  @NotNull
  private String businessNumberImage;
}
