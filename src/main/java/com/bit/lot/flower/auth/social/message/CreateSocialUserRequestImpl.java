package com.bit.lot.flower.auth.social.message;

import com.bit.lot.flower.auth.social.dto.response.UserLoginResponse;
import com.bit.lot.flower.auth.social.dto.command.SocialUserLoginCommand;
import com.bit.lot.flower.auth.social.http.feign.LoginSocialUserFeignRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateSocialUserRequestImpl implements LoginSocialUserRequest {

  private final LoginSocialUserFeignRequest feignRequest;

  @Override
  public UserLoginResponse request(SocialUserLoginCommand dto) {
   return feignRequest.login(dto).getBody();
  }
}
