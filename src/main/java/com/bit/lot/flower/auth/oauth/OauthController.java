package com.bit.lot.flower.auth.oauth;

import com.bit.lot.flower.auth.oauth.facade.OauthLoginAccessTokenRequestFacade;
import com.bit.lot.flower.auth.oauth.facade.OauthUserMeInfoRequestFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class OauthController {


  private final OauthLoginAccessTokenRequestFacade oauthLoginRequestFacade;
  private final OauthUserMeInfoRequestFacade userMeInfoRequestFacade;

//  @GetMapping("/login/oauth2/{provider}")
//  public ResponseEntity<SocialLoginRequestCommand> requestSocialInfo(@RequestParam String code, @PathVariable
//      AuthenticationProvider provider){
//      oauthLoginRequestFacade.request(provider,code);
//    return null;
//  }

}
