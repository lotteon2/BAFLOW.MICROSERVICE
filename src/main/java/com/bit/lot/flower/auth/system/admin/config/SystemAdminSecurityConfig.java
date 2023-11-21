package com.bit.lot.flower.auth.system.admin.config;


import com.bit.lot.flower.auth.common.filter.ExceptionHandlerFilter;
import com.bit.lot.flower.auth.common.filter.JwtAuthenticationFilter;
import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.system.admin.filter.SystemAdminAuthenticationFilter;
import com.bit.lot.flower.auth.system.admin.filter.SystemAdminAuthorizationFilter;
import com.bit.lot.flower.auth.system.admin.security.SystemAdminAuthenticationManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SystemAdminSecurityConfig {
  @Value("${system.admin.id}")
  private final String adminId;
  @Value("${system.admin.password}")
  private final String adminPassword;
  private final TokenHandler tokenHandler;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final ExceptionHandlerFilter exceptionHandlerFilter;
  @Bean("SystemAdminSecurityConfigFilterChain")
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.regexMatcher("/system/admin");
    http.csrf().disable();
    http.addFilterAt(systemAdminAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(systemAdminAuthorizationFilter(), JwtAuthenticationFilter.class)
        .addFilterAt(exceptionHandlerFilter, ExceptionTranslationFilter.class);
    return http.build();
  }

  @Bean
  AuthenticationManager authenticationManager() {
    return new SystemAdminAuthenticationManager(adminId, adminPassword);
  }

  @Bean
  public SystemAdminAuthenticationFilter systemAdminAuthenticationFilter() {
    return new SystemAdminAuthenticationFilter(authenticationManager(), tokenHandler);
  }

  @Bean
  public SystemAdminAuthorizationFilter systemAdminAuthorizationFilter() {
    return new SystemAdminAuthorizationFilter();
  }


}