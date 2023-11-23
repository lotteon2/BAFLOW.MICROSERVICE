package com.bit.lot.flower.auth.social.security;

import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
public class SocialAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final TokenHandler tokenHandler;

  private String createToken(HttpServletResponse response,
      Authentication authentication) {
    Map<String, Object> claimMap = JwtUtil.addClaims(
        SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME,
        Role.ROLE_SOCIAL_USER.name());
    return tokenHandler.createToken(String.valueOf(authentication.getPrincipal()),
        claimMap, response);
  }

  private SocialLoginRequestCommand getRequestDto(DefaultOAuth2User defaultOAuth2User) {
    return SocialLoginRequestCommand.builder()
        .email(defaultOAuth2User.getAttributes().get("email").toString())
        .nickname(defaultOAuth2User.getAttributes().get("nickname").toString())
        .socialId(AuthId.builder()
            .value((Long) defaultOAuth2User.getAttributes().get("id")).build()).build();

  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

    SocialLoginRequestCommand command = getRequestDto(defaultOAuth2User);
    request.setAttribute("command", command);

    String token = createToken(response, authentication);
    response.addHeader(SecurityPolicyStaticValue.TOKEN_AUTHORIZAION_HEADER_NAME,
        SecurityPolicyStaticValue.TOKEN_AUTHORIZATION_PREFIX + token);
  }

}

