package com.bit.lot.flower.auth.social.config;

import com.bit.lot.flower.auth.common.filter.ExceptionHandlerFilter;
import com.bit.lot.flower.auth.common.filter.JwtAuthenticationFilter;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.entity.SocialAuth;
import com.bit.lot.flower.auth.social.service.OAuth2UserLoadService;
import com.bit.lot.flower.auth.social.valueobject.SocialAuthId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SocialSecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final ExceptionHandlerFilter exceptionHandlerFilter;
  private final OAuth2UserLoadService oAuth2UserService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.regexMatcher("/oauth");
    http.authorizeHttpRequests(config -> config.anyRequest().permitAll());
    http.oauth2Login(oauth2Configurer -> oauth2Configurer
        .successHandler(successHandler())
        .userInfoEndpoint()
        .userService(oAuth2UserService));
    http.addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    http.addFilterAt(exceptionHandlerFilter, ExceptionTranslationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationSuccessHandler successHandler() {
    return ((request, response, authentication) -> {
      DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
      SocialLoginRequestCommand command = SocialLoginRequestCommand.builder()
          .email(defaultOAuth2User.getAttributes().get("email").toString())
          .nickname(defaultOAuth2User.getAttributes().get("nickname").toString())
          .socialId(SocialAuthId.builder()
              .value((Long) defaultOAuth2User.getAttributes().get("id")).build()).build();
      request.setAttribute("command", command);
    });
  }

}
