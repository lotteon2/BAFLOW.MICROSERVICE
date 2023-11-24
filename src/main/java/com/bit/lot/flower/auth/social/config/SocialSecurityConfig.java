package com.bit.lot.flower.auth.social.config;

import com.bit.lot.flower.auth.common.filter.ExceptionHandlerFilter;
import com.bit.lot.flower.auth.common.filter.JwtAuthenticationFilter;
import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.social.http.filter.SocialAuthorizationFilter;
import com.bit.lot.flower.auth.social.security.SocialAuthenticationSuccessHandler;
import com.bit.lot.flower.auth.social.service.OAuth2UserLoadService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SocialSecurityConfig {

  private final TokenHandler tokenHandler;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final ExceptionHandlerFilter exceptionHandlerFilter;


  @Order(1)
  @Bean
  public SecurityFilterChain socialSecurityFilterChain(HttpSecurity http) throws Exception {
    http.regexMatcher("/api/auth/social").csrf().disable()
        .authorizeHttpRequests(config -> config.anyRequest().permitAll())
        .oauth2Login(oauth2Configurer -> oauth2Configurer
            .successHandler(successHandler())
            .userInfoEndpoint()
            .userService(oAuth2UserLoadService()))
        .authenticationManager(customersAuthenticationManager())
        .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(socialAuthorizationFilter(), JwtAuthenticationFilter.class)
        .addFilterAt(exceptionHandlerFilter, ExceptionTranslationFilter.class);
    return http.build();
  }

  public AuthenticationManager customersAuthenticationManager() {
    return authentication -> {
      if (authentication.isAuthenticated()) {
        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), null,
            Collections.singleton(new SimpleGrantedAuthority("ROLE_SOCIAL_USER")));
      }
      throw new BadCredentialsException("존재하지 않은 소셜 유저입니다.");
    };
  }


  @Bean
  public DefaultOAuth2UserService oAuth2UserLoadService() {
    return new
        OAuth2UserLoadService();
  }

  @Bean
  public SocialAuthorizationFilter socialAuthorizationFilter() {
    return new SocialAuthorizationFilter();
  }


  @Bean
  public AuthenticationSuccessHandler successHandler() {
    return new SocialAuthenticationSuccessHandler(tokenHandler);
  }

}
