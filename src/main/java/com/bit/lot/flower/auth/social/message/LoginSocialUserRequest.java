package com.bit.lot.flower.auth.social.message;

import com.bit.lot.flower.auth.social.dto.response.UserFeignLoginResponse;
import com.bit.lot.flower.auth.social.dto.message.SocialUserLoginDto;
import org.springframework.stereotype.Component;


@Component
public interface LoginSocialUserRequest {
  public UserFeignLoginResponse request(SocialUserLoginDto dto);
}
