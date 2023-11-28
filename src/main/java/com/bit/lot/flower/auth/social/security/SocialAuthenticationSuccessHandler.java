package com.bit.lot.flower.auth.social.security;

import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.social.dto.Oauth2LoginDto;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.entity.SocialAuth;
import com.bit.lot.flower.auth.social.repository.SocialAuthJpaRepository;
import com.bit.lot.flower.auth.social.service.SocialLoginStrategy;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
public class SocialAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final SocialLoginStrategy socialLoginStrategy;
  private final TokenHandler tokenHandler;

  private String createToken(HttpServletResponse response,
      Authentication authentication) {
    Map<String, Object> claimMap = JwtUtil.addClaims(
        SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME,
        Role.ROLE_SOCIAL_USER.name());
    return tokenHandler.createToken(authentication.getName(),
        claimMap, response);
  }


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
    socialLoginStrategy.login(getAuthId(defaultOAuth2User.getName()));

    SocialLoginRequestCommand command = getOauth2LoginDto(defaultOAuth2User);
    request.setAttribute("command", command);

    String token = createToken(response, authentication);
    response.addHeader(SecurityPolicyStaticValue.TOKEN_AUTHORIZAION_HEADER_NAME,
        SecurityPolicyStaticValue.TOKEN_AUTHORIZATION_PREFIX + token);
  }


  private SocialLoginRequestCommand getOauth2LoginDto(OAuth2User oAuth2User) {
    LinkedHashMap<String, String> kakaoAccount = oAuth2User.getAttribute("kakao_account");
    LinkedHashMap<String, String> properties = oAuth2User.getAttribute("properties");
    String id = oAuth2User.getName();
    String email = kakaoAccount.get("email");
    String nickname = properties.get("nickname");
    return SocialLoginRequestCommand.builder().email(email).nickname(nickname)
        .socialId(AuthId.builder().value(Long.valueOf(id)).build()).build();
  }

  private AuthId getAuthId(String value) {
    return AuthId.builder().value(Long.valueOf(value)).build();
  }
}



