package com.bit.lot.flower.auth.store.entity;

import com.bit.lot.flower.auth.common.entity.BaseEntity;
import com.bit.lot.flower.auth.store.valueobject.StoreManagerStatus;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StoreManagerAuth extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String email;
  @Column(nullable = false)
  private String password;
  @Enumerated(EnumType.ORDINAL)
  private StoreManagerStatus status;
  private ZonedDateTime lastLogoutTime;
}
