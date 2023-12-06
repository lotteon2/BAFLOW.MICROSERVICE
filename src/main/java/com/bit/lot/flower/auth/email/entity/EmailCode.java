package com.bit.lot.flower.auth.email.entity;


import com.bit.lot.flower.auth.common.entity.BaseEntity;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class EmailCode extends BaseEntity {
  @Id
  private String emailCode;
  @Column(nullable = false)
  private String email;
}
