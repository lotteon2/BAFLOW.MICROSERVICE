package com.bit.lot.flower.auth.social.message;

import com.bit.lot.flower.auth.social.dto.response.UserFeignLoginResponse;
import com.bit.lot.flower.auth.social.dto.message.SocialUserLoginDto;
import com.bit.lot.flower.auth.social.http.feign.LoginSocialUserFeignRequest;
import com.bit.lot.flower.auth.social.http.valueobject.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateSocialUserEventPublisherImpl implements LoginSocialUserEventPublisher {

  private final LoginSocialUserFeignRequest feignRequest;

  @Override
  public UserFeignLoginResponse<UserId> publish(SocialUserLoginDto dto) {
   return feignRequest.login(dto).getBody();
  }
}
