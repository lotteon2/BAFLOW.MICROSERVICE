package com.bit.lot.flower.auth.social.entity;

import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NegativeOrZero.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class SocialAuth {

  @Id
  private Long oauthId;
  private boolean isRecentlyOut;
  private ZonedDateTime lastLogoutTime;
}
