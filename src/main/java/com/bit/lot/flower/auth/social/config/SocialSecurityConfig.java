package com.bit.lot.flower.auth.social.config;

import com.bit.lot.flower.auth.common.filter.ExceptionHandlerFilter;
import com.bit.lot.flower.auth.common.filter.JwtAuthenticationFilter;
import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.social.http.filter.SocialAuthenticationFilter;
import com.bit.lot.flower.auth.social.http.filter.SocialAuthorizationFilter;
import com.bit.lot.flower.auth.social.repository.SocialAuthJpaRepository;
import com.bit.lot.flower.auth.social.security.SocialAuthenticationManager;
import com.bit.lot.flower.auth.social.security.SocialAuthenticationSuccessHandler;
import com.bit.lot.flower.auth.social.service.OAuth2UserLoadService;
import com.bit.lot.flower.auth.social.service.SocialLoginStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SocialSecurityConfig {

  private final AuthenticationSuccessHandler handler;
  private final SocialLoginStrategy socialLoginStrategy;
  private final TokenHandler tokenHandler;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final ExceptionHandlerFilter exceptionHandlerFilter;

  @Order(1)
  @Bean
  public SecurityFilterChain socialSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(config -> config.anyRequest().permitAll())
        .formLogin().and().
        oauth2Login(oauth2Configurer -> oauth2Configurer
            .userInfoEndpoint()
            .userService(oAuth2UserLoadService()))
        .addFilterAfter(socialAuthenticationFilter(), OAuth2LoginAuthenticationFilter.class)
        .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(socialAuthorizationFilter(), JwtAuthenticationFilter.class)
        .addFilterAt(exceptionHandlerFilter, ExceptionTranslationFilter.class);
    return http.build();
  }

  @Bean
  AuthenticationSuccessHandler socialAuthenticationSuccessHandler() {
    return new SocialAuthenticationSuccessHandler(tokenHandler);
  }

  @Bean
  SocialAuthenticationFilter socialAuthenticationFilter() {
    SocialAuthenticationFilter authenticationFilter = new SocialAuthenticationFilter(
        socialAuthenticationManager());
    authenticationFilter.setAuthenticationSuccessHandler(handler);
    authenticationFilter.setFilterProcessesUrl("/oauth/login");
    return authenticationFilter;
  }

  @Qualifier("storeAuthenticationManager")
  @Bean
  AuthenticationManager socialAuthenticationManager() {
    return new SocialAuthenticationManager(socialLoginStrategy);
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




}
