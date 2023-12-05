package com.bit.lot.flower.auth.social.message;

import com.bit.lot.flower.auth.common.valueobject.BaseId;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.dto.response.UserFeignLoginResponse;
import com.bit.lot.flower.auth.social.http.feign.LoginSocialUserFeignRequest;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateSocialUserRequestImpl implements LoginSocialUserRequest {

  private final LoginSocialUserFeignRequest feignRequest;

  @Override
  public UserFeignLoginResponse request(SocialLoginRequestCommand dto) {
   return  feignRequest.login(dto).getBody();
  }
}
