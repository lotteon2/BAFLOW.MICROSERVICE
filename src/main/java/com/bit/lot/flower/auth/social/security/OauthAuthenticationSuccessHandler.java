package com.bit.lot.flower.auth.social.security;

import com.bit.lot.flower.auth.oauth.util.EncryptionUtil;
import com.bit.lot.flower.auth.oauth.util.UserInfoCipherHelper;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;

import com.bit.lot.flower.auth.social.exception.SocialAuthException;
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

  private final UserInfoCipherHelper userInfoCipherHelper;
  @Value("${client.redirect.domain}")
  private String oauthRedirectURL;
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
      Authentication authentication) {
    DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
    SocialLoginRequestCommand command = oauthUserInfoFacade.getCommand(defaultOAuth2User);
    try {
      response.sendRedirect(responseWithEncodedURL(oauthRedirectURL, command));
    } catch (Exception e) {
      throw new SocialAuthException("암호화를 진행할 수 없습니다.");
    }
  }

  private String responseWithEncodedURL(String oauthRedirectURL, SocialLoginRequestCommand command)
      throws Exception {
    return userInfoCipherHelper.encrpyt(oauthRedirectURL, command);
  }


}



