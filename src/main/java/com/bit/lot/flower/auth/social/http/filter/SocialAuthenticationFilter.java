package com.bit.lot.flower.auth.social.http.filter;

import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.exception.SocialAuthException;
import com.bit.lot.flower.auth.social.service.SocialLoginStrategy;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.bit.lot.flower.auth.store.dto.StoreManagerLoginDto;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SocialAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


  private final AuthenticationManager socialAuthenticationManager;

  @Autowired
  public SocialAuthenticationFilter(
      AuthenticationManager socialAuthenticationManager) {
    super(socialAuthenticationManager);
    this.socialAuthenticationManager = socialAuthenticationManager;
  }


  private SocialLoginRequestCommand getSocialLoginRequestCommand(HttpServletRequest request)
      throws IOException {

    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(request.getInputStream(),
        SocialLoginRequestCommand.class);

  }

  private Authentication getAuthenticationFromCommand(SocialLoginRequestCommand command) {
    return new UsernamePasswordAuthenticationToken(command.getSocialId(), null,
        Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_SOCIAL_USER.name())));
  }

  @SneakyThrows
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
}