package com.bit.lot.flower.auth.common.util;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;

public class CookieUtil {

  public static HttpCookie createHttpOnlyCookie(String name, String value, Duration maxAge,
      String path) {
    return ResponseCookie.from(name, value)
        .httpOnly(true)
        .maxAge(maxAge)
        .path(path)
        .build();
  }
  public static String getCookieValue(HttpServletRequest request, String name) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      Optional<Cookie> matchingCookie = Arrays.stream(cookies)
          .filter(cookie -> name.equals(cookie.getName()))
          .findFirst();

      if (matchingCookie.isPresent()) {
        return matchingCookie.get().getValue();
      }
    }
     throw new IllegalArgumentException("존재하지 않는 쿠키입니다.");
  }

      public static HttpCookie deleteCookie(String name, String path) {
        return ResponseCookie.from(name, "")
                .httpOnly(true)
                .maxAge(Duration.ofSeconds(0))
                .path(path)
                .build();
    }
}