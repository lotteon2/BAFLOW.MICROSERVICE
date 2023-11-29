package com.bit.lot.flower.auth.social.config;

import com.bit.lot.flower.auth.common.filter.ExceptionHandlerFilter;
import com.bit.lot.flower.auth.common.filter.JwtAuthenticationFilter;
import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.social.http.filter.SocialAuthenticationFilter;
import com.bit.lot.flower.auth.social.http.filter.SocialAuthorizationFilter;
import com.bit.lot.flower.auth.social.security.SocialAuthenticationSuccessHandler;
import com.bit.lot.flower.auth.social.service.OAuth2UserLoadService;
import com.bit.lot.flower.auth.social.service.SocialLoginStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class OauthSecurityConfig {



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
        .formLogin().and().
        oauth2Login(oauth2Configurer -> oauth2Configurer.successHandler(
                socialAuthenticationSuccessHandler())
            .userInfoEndpoint()
            .userService(oAuth2UserLoadService()));
    return http.build();
  }

  @Qualifier("socialAuthenticationSuccessHandler")
  @Bean
  AuthenticationSuccessHandler socialAuthenticationSuccessHandler() {
    return new SocialAuthenticationSuccessHandler();
  }



  @Bean
  public DefaultOAuth2UserService oAuth2UserLoadService() {
    return new
        OAuth2UserLoadService();
  }





}
