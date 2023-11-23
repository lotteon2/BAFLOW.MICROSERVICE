package com.bit.lot.flower.auth.social.http.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "oauth2")
public interface OauthFeignClientRequest {
    @PostMapping("/kauth.kakao.com/v1/user/logout")
    void kakaoLogout(@RequestHeader("Authorization") String accessToken);
}
