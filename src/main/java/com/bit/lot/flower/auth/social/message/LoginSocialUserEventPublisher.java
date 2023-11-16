package com.bit.lot.flower.auth.social.message;

import com.bit.lot.flower.auth.social.dto.message.SocialUserCreateDto;
import com.bit.lot.flower.auth.social.dto.response.LoginSuccessResponse;
import org.springframework.stereotype.Component;


@Component
public interface LoginSocialUserEventPublisher {
  public LoginSuccessResponse publish(SocialUserCreateDto dto);
}
