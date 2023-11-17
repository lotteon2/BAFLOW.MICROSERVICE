package com.bit.lot.flower.auth.store.config;

import com.bit.lot.flower.auth.common.filter.ExceptionHandlerFilter;
import com.bit.lot.flower.auth.common.filter.JwtAuthenticationFilter;
import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.store.filter.StoreManagerAuthenticationFilter;
import com.bit.lot.flower.auth.store.filter.StoreMangerAuthorizationFilter;
import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import com.bit.lot.flower.auth.store.security.StoreAuthenticationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class StoreManagerSecurityConfig {

  private final StoreManagerAuthRepository repository;
  private final TokenHandler tokenHandler;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final ExceptionHandlerFilter exceptionHandlerFilter;
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.regexMatcher("/stores");
    http.csrf().disable();
    http.
        addFilterAt(storeManagerAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).
        addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).
        addFilterAfter(storeMangerAuthorizationFilter(), JwtAuthenticationFilter.class)
        .addFilterAt(exceptionHandlerFilter, ExceptionTranslationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager customManger() {
    return new StoreAuthenticationManager(repository,passwordEncoder());
  }


  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public StoreManagerAuthenticationFilter storeManagerAuthenticationFilter() {
    return new StoreManagerAuthenticationFilter(customManger(), tokenHandler);
  }

  @Bean
  public StoreMangerAuthorizationFilter storeMangerAuthorizationFilter() {
    return new StoreMangerAuthorizationFilter();
  }


}
