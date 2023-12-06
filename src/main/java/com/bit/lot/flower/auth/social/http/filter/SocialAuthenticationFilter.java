package com.bit.lot.flower.auth.social.http.filter;

import com.bit.lot.flower.auth.common.security.SystemAuthenticationSuccessHandler;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.exception.SocialAuthException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class SocialAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final SystemAuthenticationSuccessHandler handler;
  private final AuthenticationManager socialAuthenticationManager;

  @Autowired
  public SocialAuthenticationFilter(
      SystemAuthenticationSuccessHandler handler,
      AuthenticationManager socialAuthenticationManager) {
    super(socialAuthenticationManager);
    this.handler = handler;
    this.socialAuthenticationManager = socialAuthenticationManager;
  }


  private SocialLoginRequestCommand getSocialLoginRequestCommand(HttpServletRequest request) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      SocialLoginRequestCommand command = mapper.readValue(request.getInputStream(),
          SocialLoginRequestCommand.class);
      request.setAttribute("command", command);
      return command;
    } catch (IOException e) {
      throw new SocialAuthException("잘못된 입력입니다. 아이디 패스워드를 입력해주세요.");
    }
  }

  private Authentication getAuthenticationFromCommand(SocialLoginRequestCommand command) {
    return new UsernamePasswordAuthenticationToken(command.getSocialId(), null,
        Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_SOCIAL_USER.name())));
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) {
    SocialLoginRequestCommand command = getSocialLoginRequestCommand(request);
    try {
      Authentication authentication = getAuthenticationFromCommand(command);
      socialAuthenticationManager.authenticate(authentication);
      return authentication;

    } catch (SocialAuthException e) {
      throw new SocialAuthException(e.getMessage());
    }
  }

  @Override
  public void successfulAuthentication(HttpServletRequest request, HttpServletResponse
      response, FilterChain
      chain,
      Authentication authResult) throws IOException, ServletException {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authResult);
    SecurityContextHolder.setContext(context);
    handler.onAuthenticationSuccess(request, response,chain, authResult);
  }


}