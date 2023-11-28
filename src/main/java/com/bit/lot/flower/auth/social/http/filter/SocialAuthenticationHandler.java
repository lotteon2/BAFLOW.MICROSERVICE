package com.bit.lot.flower.auth.social.http.filter;

import com.bit.lot.flower.auth.social.valueobject.AuthId;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SocialAuthenticationHandler implements
    AuthenticationSuccessHandler {


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    AuthId authId = getUserAuthId(authentication);
  }

  private AuthId createAuthId(String id) {
    return AuthId.builder().value(Long.valueOf(id)).build();
  }

  private AuthId getUserAuthId(Authentication authentication) {
    DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
    return createAuthId(defaultOAuth2User.getName());
  }
}
