package com.bit.lot.flower.auth.social.config;

import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.service.OAuth2UserLoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
@EnableWebSecurity
public class SocialSecurityConfig {

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

    return http.build();
  }

  @Bean
  public AuthenticationSuccessHandler successHandler() {
    return ((request, response, authentication) -> {
      DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
      SocialLoginRequestCommand command = SocialLoginRequestCommand.builder()
          .email(defaultOAuth2User.getAttributes().get("email").toString())
          .nickname(defaultOAuth2User.getAttributes().get("nickname").toString())
          .socialId(defaultOAuth2User.getAttributes().get("id").toString()).build();

      request.setAttribute("command", command);
    });
  }
}
