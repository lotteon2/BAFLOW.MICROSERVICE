package com.bit.lot.flower.auth.common.config;

import com.bit.lot.flower.auth.common.http.interceptor.CommonLogoutInterceptor;
import com.bit.lot.flower.auth.common.security.JwtTokenProcessor;
import com.bit.lot.flower.auth.common.security.TokenHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

  private final TokenHandler tokenHandler;
  private final JwtTokenProcessor jwtTokenProcessor;

  @Bean
  public CommonLogoutInterceptor commonLogoutInterceptor() {
    return new CommonLogoutInterceptor(jwtTokenProcessor, tokenHandler
    );
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(commonLogoutInterceptor())
        .excludePathPatterns(
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/webjars/**"
        ).addPathPatterns("/**/logout");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("http://localhost:3000", "http://localhost:3001", "http://localhost:3002",
            "https://blooming.blooms.mall.stockey.kr",
            "https://blooming.blooms.store.stockey.kr",
            "https://blooming.blooms.admin.stockey.kr")
        .allowedMethods("*")
        .allowedHeaders("*")
        .exposedHeaders("*")
        .allowCredentials(true);

  }


}
