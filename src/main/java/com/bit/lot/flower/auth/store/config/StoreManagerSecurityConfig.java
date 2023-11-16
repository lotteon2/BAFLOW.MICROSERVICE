package com.bit.lot.flower.auth.store.config;

import com.bit.lot.flower.auth.common.filter.JwtAuthenticationFilter;
import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.store.entity.StoreManagerAuth;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.bit.lot.flower.auth.store.filter.StoreManagerAuthenticationFilter;
import com.bit.lot.flower.auth.store.filter.StoreMangerAuthorizationFilter;
import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import com.bit.lot.flower.auth.store.service.StoreManagerService;
import com.bit.lot.flower.auth.store.valueobject.StoreManagerStatus;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class StoreManagerSecurityConfig {

  private final StoreManagerAuthRepository repository;
  private final TokenHandler tokenHandler;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.regexMatcher("/stores");
    http.
        authorizeHttpRequests().antMatchers("/auth/stores/login").permitAll().
        antMatchers("/auth/stores/business-number")
        .hasRole(StoreManagerStatus.ROLE_STORE_MANAGER_DENIED.name()).anyRequest().hasRole(
            Role.ROLE_SOCIAL_MANAGER.name());

    http.
        addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).
        addFilterAfter(storeMangerAuthorizationFilter(), JwtAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  AuthenticationManager customManger() {
    return new AuthenticationManager() {
      @Override
      public Authentication authenticate(Authentication authentication)
          throws AuthenticationException {

        Long storeManagerId = (Long) authentication.getPrincipal();
        StoreManagerAuth storeManagerAuth = repository.findById(storeManagerId).orElseThrow(() -> {
          throw new BadCredentialsException("존재하지 않는 스토어 매니저입니다.");
        });
        if (storeManagerAuth.getStatus().equals(StoreManagerStatus.ROLE_STORE_MANAGER_PENDING)) {
          throw new StoreManagerAuthException("관리자의 승인을 기다려주새요.");
        }
        return new UsernamePasswordAuthenticationToken(storeManagerId,
            storeManagerAuth.getPassword(), Collections.singleton(
            new SimpleGrantedAuthority(storeManagerAuth.getStatus().toString())));
      }
    };
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
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
