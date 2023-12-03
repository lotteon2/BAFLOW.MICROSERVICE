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
public class CreateSocialUserRequestImpl<ID extends BaseId> implements LoginSocialUserRequest<ID> {

  private final LoginSocialUserFeignRequest<ID> feignRequest;

  @Override
  public UserFeignLoginResponse request(SocialLoginRequestCommand<ID> dto) {
   return (UserFeignLoginResponse) feignRequest.login(dto).getBody();
  }
}
