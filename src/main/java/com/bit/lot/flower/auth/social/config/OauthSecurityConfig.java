package com.bit.lot.flower.auth.social.config;

import com.bit.lot.flower.auth.social.security.OauthAuthenticationSuccessHandler;
import com.bit.lot.flower.auth.social.service.OAuth2UserLoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class OauthSecurityConfig {

  private final OauthAuthenticationSuccessHandler successHandler;


  @Order(0)
  @Bean
  public SecurityFilterChain oauthSecurityFilterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeRequests(authorize -> authorize
            .regexMatchers("/login/oauth2/.*$").permitAll()
            .regexMatchers("/oauth2/authorization/.*$").permitAll()
            .regexMatchers("/login").permitAll()
            .regexMatchers("kauth.*$").permitAll()
            .regexMatchers("^kapi.*$").permitAll())
        .formLogin().disable().
        oauth2Login(oauth2Configurer -> oauth2Configurer.successHandler(
                successHandler)
            .userInfoEndpoint()
            .userService(oAuth2UserLoadService()));
    return http.build();
  }


  @Bean
  public DefaultOAuth2UserService oAuth2UserLoadService() {
    return new
        OAuth2UserLoadService();
  }





}
