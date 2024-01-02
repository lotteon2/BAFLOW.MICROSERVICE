package com.bit.lot.flower.auth.social.http.feign;

import com.bit.lot.flower.auth.social.http.valueobject.UserId;
import org.springframework.stereotype.Component;

@Component
public interface UserWithdrawalRequest<ID extends UserId> {
  public void request(ID id);
}
