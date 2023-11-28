package com.bit.lot.flower.auth.social.http.filter;

import com.bit.lot.flower.auth.social.repository.SocialAuthJpaRepository;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SocialAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager socialAuthenticationManager;

  @Autowired
  public SocialAuthenticationFilter(
      @Qualifier("socialAuthenticationManager") AuthenticationManager socialAuthenticationManager) {
    super(socialAuthenticationManager);
    this.socialAuthenticationManager = socialAuthenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) {
    return socialAuthenticationManager.authenticate(
        SecurityContextHolder.getContext().getAuthentication());
  }

}
