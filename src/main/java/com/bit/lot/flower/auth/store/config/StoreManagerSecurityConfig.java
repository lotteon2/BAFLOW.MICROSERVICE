package com.bit.lot.flower.auth.store.config;

import com.bit.lot.flower.auth.common.filter.JwtAuthenticationFilter;
import com.bit.lot.flower.auth.store.entity.StoreManager;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
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
public class StoreManagerSecurityConfig {

  private final StoreManagerAuthRepository repository;


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.regexMatcher("/stores");
    http.
        authorizeHttpRequests().antMatchers("/stores/login").permitAll();
    http.addFilterAfter(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .build();
    return http.build();
  }

  @Bean
  AuthenticationManager customManger() {
    return new AuthenticationManager() {
      @Override
      public Authentication authenticate(Authentication authentication)
          throws AuthenticationException {

        Long storeManagerId = (Long) authentication.getPrincipal();
        StoreManager storeManager = repository.findById(storeManagerId).orElseThrow(() -> {
          throw new StoreManagerAuthException("존재하지 않는 스토어 매니저입니다.");
        });

        return new UsernamePasswordAuthenticationToken(storeManagerId, null, Collections.singleton(
            new SimpleGrantedAuthority(storeManager.getStatus().toString())));
        }
    };
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }

}
