package com.bit.lot.flower.auth.email.entity;


import com.sun.istack.NotNull;
import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class EmailCode {

  @Id
  private String emailCode;
  private ZonedDateTime createdAt;
  private String email;
}
