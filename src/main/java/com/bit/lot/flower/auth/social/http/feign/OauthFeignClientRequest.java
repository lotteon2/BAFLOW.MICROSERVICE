package com.bit.lot.flower.auth.social.http.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "oauth2", url = "https://kauth.kakao.com")
public interface OauthFeignClientRequest {

    @GetMapping("/oauth/logout")
    ResponseEntity<Long> kakaoLogout(@RequestParam String client_id,
        @RequestParam String logout_redirect_uri);
}
