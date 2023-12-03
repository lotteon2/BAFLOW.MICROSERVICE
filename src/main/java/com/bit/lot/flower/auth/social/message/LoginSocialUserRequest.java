package com.bit.lot.flower.auth.social.message;

import com.bit.lot.flower.auth.common.valueobject.BaseId;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.dto.response.UserFeignLoginResponse;
import org.springframework.stereotype.Component;


@Component
public interface LoginSocialUserRequest {
  public UserFeignLoginResponse request(SocialLoginRequestCommand dto);
}
