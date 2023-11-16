package com.bit.lot.flower.auth.store.entity;

import com.bit.lot.flower.auth.store.valueobject.StoreManagerStatus;
import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StoreManager {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;
  private String password;
  private StoreManagerStatus status;
  private boolean isPermitted;
  private ZonedDateTime lastLogoutTime;
}
