package com.bit.lot.flower.auth.social.config;

import com.bit.lot.flower.auth.common.http.interceptor.filter.ExceptionHandlerFilter;
import com.bit.lot.flower.auth.common.http.interceptor.filter.JwtAuthenticationFilter;
import com.bit.lot.flower.auth.common.security.SystemAuthenticationSuccessHandler;
import com.bit.lot.flower.auth.social.http.filter.SocialAuthenticationFilter;
import com.bit.lot.flower.auth.social.http.filter.SocialAuthorizationFilter;
import com.bit.lot.flower.auth.social.security.SocialAuthenticationManager;
import com.bit.lot.flower.auth.social.service.SocialLoginStrategy;
import com.bit.lot.flower.auth.common.valueobject.AuthId;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SocialSecurityConfig {

  private final SystemAuthenticationSuccessHandler systemAuthenticationSuccessHandler;
  private final SocialLoginStrategy<AuthId> socialLoginStrategy;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final ExceptionHandlerFilter exceptionHandlerFilter;


  @Order(1)
  @Bean
  public SecurityFilterChain socialSecurityFilterChain(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.authorizeRequests(
        authorize -> authorize.regexMatchers("^\\/api\\/auth\\/social\\/.*$").permitAll());
    http
        .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(socialAuthorizationFilter(), JwtAuthenticationFilter.class)
        .addFilterAt(exceptionHandlerFilter, ExceptionTranslationFilter.class);
    return http.build();
  }

  @Bean
  SocialAuthorizationFilter socialAuthorizationFilter() {
    return new SocialAuthorizationFilter();
  }

  @Qualifier("socialAuthenticationManager")
  @Bean
  AuthenticationManager socialAuthenticationManager() {
    return new SocialAuthenticationManager(socialLoginStrategy);
  }

  @Qualifier("socialAuthenticationFilter")
  @Bean
  UsernamePasswordAuthenticationFilter socialAuthenticationFilter() {
    SocialAuthenticationFilter socialAuthenticationFilter = new SocialAuthenticationFilter(systemAuthenticationSuccessHandler,
        socialAuthenticationManager());
    socialAuthenticationFilter.setFilterProcessesUrl("/api/auth/social/**/login");
    socialAuthenticationFilter.setAuthenticationSuccessHandler(systemAuthenticationSuccessHandler);
    return socialAuthenticationFilter;
  }

}
