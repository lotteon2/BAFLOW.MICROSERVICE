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
  private final TokenHandler tokenHandler;

  @Autowired
  public SocialAuthenticationFilter(
      AuthenticationManager socialAuthenticationManager,
      TokenHandler tokenHandler) {
    super(socialAuthenticationManager);
    this.socialAuthenticationManager = socialAuthenticationManager;
    this.tokenHandler = tokenHandler;
  }

  private String createToken(HttpServletResponse response,
      String oauthId) {
    Map<String, Object> claimMap = JwtUtil.addClaims(
        SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME,
        Role.ROLE_SOCIAL_USER.name());
    return tokenHandler.createToken(oauthId,
        claimMap, response);
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
      String token = createToken(response, command.getSocialId().toString());
      response.addHeader(SecurityPolicyStaticValue.TOKEN_AUTHORIZAION_HEADER_NAME,
          SecurityPolicyStaticValue.TOKEN_AUTHORIZATION_PREFIX + token);
      return authentication;
    } catch (SocialAuthException e) {
      throw new StoreManagerAuthException(e.getMessage());
    }

  }
}