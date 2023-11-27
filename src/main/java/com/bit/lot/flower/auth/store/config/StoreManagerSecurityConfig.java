package com.bit.lot.flower.auth.store.config;

import com.bit.lot.flower.auth.common.filter.ExceptionHandlerFilter;
import com.bit.lot.flower.auth.common.filter.JwtAuthenticationFilter;
import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.store.http.filter.StoreManagerAuthenticationFilter;
import com.bit.lot.flower.auth.store.http.filter.StoreMangerAuthorizationFilter;
import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import com.bit.lot.flower.auth.store.security.StoreAuthenticationManager;
import com.bit.lot.flower.auth.store.valueobject.StoreManagerStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class StoreManagerSecurityConfig {

  private final StoreManagerAuthRepository repository;
  private final TokenHandler tokenHandler;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final ExceptionHandlerFilter exceptionHandlerFilter;



  @Order(2)
  @Bean
  public SecurityFilterChain storeSecurityFilterChain(HttpSecurity http) throws Exception {
      http.regexMatcher("^.*\\/stores\\/.*$");
    http.csrf().disable();
    http.
        addFilterAt(storeManagerAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).
        addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).
        addFilterAfter(storeMangerAuthorizationFilter(), JwtAuthenticationFilter.class)
        .addFilterAt(exceptionHandlerFilter, ExceptionTranslationFilter.class);

    return http.build();
  }

  @Primary
  @Qualifier("storeAuthenticationManager")
  @Bean
  public AuthenticationManager storeAuthenticationManager() {
    return new StoreAuthenticationManager(repository,passwordEncoder());
  }


  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public StoreManagerAuthenticationFilter storeManagerAuthenticationFilter() {
    StoreManagerAuthenticationFilter authenticationFilter = new StoreManagerAuthenticationFilter(
        storeAuthenticationManager(), tokenHandler);
    authenticationFilter.setFilterProcessesUrl("/**/stores/login");
    return authenticationFilter;
  }

  @Bean
  public StoreMangerAuthorizationFilter storeMangerAuthorizationFilter() {
    return new StoreMangerAuthorizationFilter();
  }


}
