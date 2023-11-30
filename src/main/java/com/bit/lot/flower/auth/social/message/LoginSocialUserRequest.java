package com.bit.lot.flower.auth.social.message;

import com.bit.lot.flower.auth.social.dto.response.UserLoginResponse;
import com.bit.lot.flower.auth.social.dto.command.SocialUserLoginCommand;
import org.springframework.stereotype.Component;


@Component
public interface LoginSocialUserRequest {
  public UserLoginResponse request(SocialUserLoginCommand dto);
}
