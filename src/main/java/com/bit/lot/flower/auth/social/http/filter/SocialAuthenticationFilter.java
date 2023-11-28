package com.bit.lot.flower.auth.social.http.filter;

import com.bit.lot.flower.auth.common.security.SecurityContextUtil;
import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.social.exception.SocialAuthException;
import com.bit.lot.flower.auth.social.service.SocialLoginStrategy;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.bit.lot.flower.auth.store.dto.StoreManagerLoginDto;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class SocialAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


  private final SocialLoginStrategy socialLoginStrategy;
  private final TokenHandler tokenHandler;


  private String createToken(HttpServletResponse response,
      String oauthId) {
    Map<String, Object> claimMap = JwtUtil.addClaims(
        SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME,
        Role.ROLE_SOCIAL_USER.name());
    return tokenHandler.createToken(oauthId,
        claimMap, response);
  }

  private AuthId getAuthIdFromContext() {
    Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return AuthId.builder().value(Long.valueOf(String.valueOf(principle))).build();
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) {
    AuthId authId = getAuthIdFromContext();
    try {
      socialLoginStrategy.login(authId);
      String token = createToken(response, authId.toString());
      response.addHeader(SecurityPolicyStaticValue.TOKEN_AUTHORIZAION_HEADER_NAME,
          SecurityPolicyStaticValue.TOKEN_AUTHORIZATION_PREFIX + token);
    } catch (SocialAuthException e) {
      throw new StoreManagerAuthException(e.getMessage());
    }
    return new UsernamePasswordAuthenticationToken(authId, null,
        Collections.singleton(new SimpleGrantedAuthority(
            Role.ROLE_SOCIAL_USER.name())));
  }
}