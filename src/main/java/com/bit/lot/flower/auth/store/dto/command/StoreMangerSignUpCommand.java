package com.bit.lot.flower.auth.store.dto.command;

import com.bit.lot.flower.auth.store.http.validator.ValidPassword;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StoreMangerSignUpCommand {

  @Email
  @NotNull
  private String email;
  @Length(max=6)
  @NotNull
  private String name;
  @NotNull
  private Boolean isEmailVerified;
  @ValidPassword
  @Length(min = 6)
  @NotNull
  private String password;
  @URL
  @NotNull
  private String businessNumberImage;
}
