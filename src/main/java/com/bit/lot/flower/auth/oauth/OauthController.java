package com.bit.lot.flower.auth.oauth;

import com.bit.lot.flower.auth.common.valueobject.AuthenticationProvider;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class OauthController {


  private OauthLoginCodeRequestFacade oauthLoginRequestFacade;
  @GetMapping("/login/oauth2/code/{provider}")
  public ResponseEntity<SocialLoginRequestCommand> requestSocialInfo(@RequestParam String code, @PathVariable
      AuthenticationProvider provider){
    restTemplate.

  }

}
