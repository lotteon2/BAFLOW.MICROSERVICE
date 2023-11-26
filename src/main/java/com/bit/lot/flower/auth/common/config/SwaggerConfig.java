package com.bit.lot.flower.auth.common.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

  private static final String API_NAME = "Auth API";
  private static final String API_VERSION = "0.0.1";
  private static final String API_DESCRIPTION = "Auth API 명세서";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("**/**").addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }


  @Bean
  public Docket api() {
    Parameter parameterBuilder = new ParameterBuilder()
        .name(HttpHeaders.AUTHORIZATION)
        .description("Access Token")
        .modelRef(new ModelRef("string"))
        .parameterType("header")
        .required(false)
        .build();

    List<Parameter> globalParameters = new ArrayList<>();
    globalParameters.add(parameterBuilder);

    return new Docket(DocumentationType.SWAGGER_2)
        .globalOperationParameters(globalParameters)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .paths(PathSelectors.any())
        .build();
  }


  public ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title(API_NAME)
        .version(API_VERSION)
        .description(API_DESCRIPTION)
        .build();
  }
}