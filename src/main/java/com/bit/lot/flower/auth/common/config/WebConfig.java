package com.bit.lot.flower.auth.common.config;

import com.bit.lot.flower.auth.common.interceptor.CommonLogoutInterceptor;
import com.bit.lot.flower.auth.social.http.interceptor.SocialAuthLoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

  private final SocialAuthLoginInterceptor socialAuthLoginInterceptor;
  private final CommonLogoutInterceptor logoutInterceptor;
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(logoutInterceptor).addPathPatterns("/logout");
    registry.addInterceptor(socialAuthLoginInterceptor).addPathPatterns("/social/login");
  }



}
