package com.bit.lot.flower.auth.system.admin.config;


import com.bit.lot.flower.auth.common.filter.JwtAuthenticationFilter;
import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.system.admin.filter.SystemAdminAuthenticationFilter;
import com.bit.lot.flower.auth.system.admin.filter.SystemAdminAuthorizationFilter;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SystemAdminSecurityConfig {

  @Value("${system.admin.id}")
  private final String adminId;
  @Value("${system.admin.password}")
  private final String adminPassword;
  private final TokenHandler tokenHandler;
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.regexMatcher("/system/admin")
        .authorizeRequests()
        .antMatchers("/system/admin/login").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilterAt(systemAdminAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(systemAdminAuthorizationFilter(), JwtAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }

  @Bean
  public SystemAdminAuthenticationFilter systemAdminAuthenticationFilter() {
    return new SystemAdminAuthenticationFilter(adminId, adminPassword, authenticationManager(),tokenHandler);
  }

  @Bean
  public SystemAdminAuthorizationFilter systemAdminAuthorizationFilter() {
    return new SystemAdminAuthorizationFilter();
  }


  @Bean
  AuthenticationManager authenticationManager() {
    return new AuthenticationManager() {
      @Override
      public Authentication authenticate(Authentication authentication)
          throws AuthenticationException {
        String inputId = (String) authentication.getPrincipal();
        String inputPassword = (String) authentication.getCredentials();
        if (!inputId.equals(adminId) || !inputPassword.equals(adminPassword)) {
          throw new BadCredentialsException("시스템 어드민의 아이디와 비밀번호가 일치하지 않습니다.");
        }

        return new UsernamePasswordAuthenticationToken(inputId, inputPassword,
            Collections.singleton(new SimpleGrantedAuthority(
                Role.ROLE_SYSTEM_ADMIN.toString())));
      }
    };
  }
}