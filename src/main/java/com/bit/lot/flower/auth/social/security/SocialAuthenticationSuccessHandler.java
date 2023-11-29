package com.bit.lot.flower.auth.social.security;

import com.bit.lot.flower.auth.social.dto.message.SocialUserLoginDto;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.LinkedHashMap;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@RequiredArgsConstructor
public class SocialAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain,
      Authentication authentication) throws IOException, ServletException {
    onAuthenticationSuccess(request, response, authentication);
    chain.doFilter(request, response);
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
    SocialUserLoginDto command = getOauth2LoginDto(defaultOAuth2User);
    setCustomResponseWithLoginDto(response, command);

  }

  private HttpServletResponse setCustomResponseWithLoginDto(HttpServletResponse response,
      SocialUserLoginDto dto)
      throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(dto);
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(json);
    return response;
  }

  private SocialUserLoginDto getOauth2LoginDto(OAuth2User oAuth2User) {
    LinkedHashMap<String, String> kakaoAccount = oAuth2User.getAttribute("kakao_account");
    LinkedHashMap<String, String> properties = oAuth2User.getAttribute("properties");
    String id = oAuth2User.getName();
    String email = kakaoAccount.get("email");
    String nickname = properties.get("nickname");
    return SocialUserLoginDto.builder().email(email).nickname(nickname)
        .socialId(AuthId.builder().value(Long.valueOf(id)).build()).build();
  }

}



