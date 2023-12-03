package com.bit.lot.flower.auth.social.security;

import com.bit.lot.flower.auth.common.util.JsonBinderUtil;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import java.io.IOException;
import java.util.LinkedHashMap;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OauthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain,
      Authentication authentication) throws IOException, ServletException {
    onAuthenticationSuccess(request, response, authentication);
    chain.doFilter(request, response);
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
    SocialLoginRequestCommand command = getOauth2LoginDto(defaultOAuth2User);
    JsonBinderUtil.setResponseWithJson(response,200, command);

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

}



