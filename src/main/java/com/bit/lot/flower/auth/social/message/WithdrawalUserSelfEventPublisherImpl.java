package com.bit.lot.flower.auth.social.message;

import com.bit.lot.flower.auth.social.dto.command.SoftDeleteSocialUserCommand;
import com.bit.lot.flower.auth.social.http.feign.SoftDeleteUserFeignRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WithdrawalUserSelfEventPublisherImpl implements
    WithdrawalUserSelfEventPublisher {
  private final SoftDeleteUserFeignRequest request;
  @Override
  public void delete(Long oauthId) {
    request.delete(new SoftDeleteSocialUserCommand(oauthId));
  }
}
