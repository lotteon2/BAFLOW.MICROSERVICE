package com.bit.lot.flower.auth.system.admin.config;


import com.bit.lot.flower.auth.common.filter.ExceptionHandlerFilter;
import com.bit.lot.flower.auth.common.filter.JwtAuthenticationFilter;
import com.bit.lot.flower.auth.common.security.SystemAuthenticationSuccessHandler;
import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.system.admin.http.filter.SystemAdminAuthenticationFilter;
import com.bit.lot.flower.auth.system.admin.http.filter.SystemAdminAuthorizationFilter;
import com.bit.lot.flower.auth.system.admin.security.SystemAdminAuthenticationManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import lombok.RequiredArgsConstructor;
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
public class SystemAdminSecurityConfig {

  @Value("${system.admin.id}")
  private String adminId;
  @Value("${system.admin.password}")
  private String adminPassword;
  private final SystemAuthenticationSuccessHandler authenticationSuccessHandler;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final ExceptionHandlerFilter exceptionHandlerFilter;


  @Order(3)
  @Bean
  public SecurityFilterChain systemSecurityFilterChain(HttpSecurity http) throws Exception {

    http
        .regexMatcher("^.*\\/admin\\/.*$");
    http.csrf().disable()
        .addFilterAt(systemAdminAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(systemAdminAuthorizationFilter(), JwtAuthenticationFilter.class)
        .addFilterAt(exceptionHandlerFilter, ExceptionTranslationFilter.class);
    return http.build();
  }


  @Qualifier("systemAuthenticationManager")
  @Bean
  AuthenticationManager systemAuthenticationManager() {
    return new SystemAdminAuthenticationManager(adminId, adminPassword);
  }

  @Bean
  public SystemAdminAuthenticationFilter systemAdminAuthenticationFilter() {
    SystemAdminAuthenticationFilter systemAdminAuthenticationFilter = new SystemAdminAuthenticationFilter(
        systemAuthenticationManager());
    systemAdminAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
    systemAdminAuthenticationFilter.setFilterProcessesUrl("/**/admin/login");
    return systemAdminAuthenticationFilter;
  }

  @Bean
  public SystemAdminAuthorizationFilter systemAdminAuthorizationFilter() {
    return new SystemAdminAuthorizationFilter();
  }


}