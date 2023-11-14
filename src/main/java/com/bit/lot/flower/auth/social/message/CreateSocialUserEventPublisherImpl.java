package com.bit.lot.flower.auth.social.message;

import com.bit.lot.flower.auth.social.dto.message.SocialUserDto;
import com.bit.lot.flower.auth.social.dto.response.LoginSuccessResponse;
import com.bit.lot.flower.auth.social.http.feign.LoginSocialUserFeignRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateSocialUserEventPublisherImpl implements LoginSocialUserEventPublisher {

  private final LoginSocialUserFeignRequest feignRequest;

  @Override
  public LoginSuccessResponse publish(SocialUserDto dto) {
   ResponseEntity<LoginSuccessResponse> response = feignRequest.login(dto);
   return response.getBody();
  }
}
