package com.bit.lot.flower.auth.social.security;

import com.bit.lot.flower.auth.common.util.JsonBinderUtil;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OauthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Value("${kakao.login.redirect.url}")
  private String redirectURL;
  private final OauthUserInfoFacade oauthUserInfoFacade;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain,
      Authentication authentication) throws IOException, ServletException {
    onAuthenticationSuccess(request, response, authentication);
    chain.doFilter(request, response);
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
    SocialLoginRequestCommand command = oauthUserInfoFacade.getCommand(defaultOAuth2User);
    JsonBinderUtil.setResponseWithJson(response, 200, command);
  }



}



