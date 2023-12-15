package com.bit.lot.flower.auth.common.util;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

public class CookieUtil {



  public static ResponseCookie createRefreshNoCORSCookie(String name, String value, Duration maxAge,
      String path) {
    return ResponseCookie.from(name, value)
        .maxAge(maxAge)
        .path(path)
        .secure(true)
        .sameSite("None")
        .httpOnly(true)
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

  public static Cookie deleteCookie(String name, String path) {
    Cookie cookie = new Cookie(name, null);
    cookie.setMaxAge(0);
    cookie.setSecure(true);
    cookie.setHttpOnly(true);
    cookie.setPath(path);
    return cookie;
  }
}