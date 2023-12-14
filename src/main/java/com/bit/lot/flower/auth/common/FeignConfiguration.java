package com.bit.lot.flower.auth.common;

import com.bit.lot.flower.auth.social.http.feign.OauthFeignClientRequest;
import feign.Client;
import feign.Feign;
import feign.Request;
import feign.Response;
import java.io.IOException;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class FeignConfiguration {


  @Bean
  public OauthFeignClientRequest oauthFeignClientRequest() {
    return Feign.builder()
        .contract(new SpringMvcContract())
        .client(new NotThrowRuntimeExceptionWhen302Status())
        .target(OauthFeignClientRequest.class, "https://kauth.kakao.com");
  }


  public static class NotThrowRuntimeExceptionWhen302Status implements Client {

    private final Client delegate = new Client.Default(null, null);

    @Override
    public Response execute(Request request, Request.Options options)
        throws IOException {
      Response response = delegate.execute(request, options);
      if (response.status() == 302) {
        return response;
      }
      return response;
    }

  }
}
