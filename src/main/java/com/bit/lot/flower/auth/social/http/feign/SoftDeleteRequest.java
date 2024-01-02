package com.bit.lot.flower.auth.social.http.feign;

import com.bit.lot.flower.auth.social.http.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SoftDeleteRequest implements UserWithdrawalRequest<UserId> {

  private final SocialDeleteFeignRequest feignRequest;
  @Override
  public void request(UserId id) {
    feignRequest.delete(id.getValue());
  }
}
