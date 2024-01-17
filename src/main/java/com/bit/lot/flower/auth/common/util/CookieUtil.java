package com.bit.lot.flower.auth.common.util;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;

public class CookieUtil {


  private CookieUtil(){

  }

  public static Cookie createCookie(String name, String value, int maxAge,String domain) {
   Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setDomain(domain);
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        return cookie;
  }

  public static ResponseCookie createRefreshNoCORSCookie(String name, String value, Duration maxAge,String domain) {
    return ResponseCookie.from(name, value)
        .maxAge(maxAge)
        .path("/")
        .domain(domain)
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
        throw new IllegalArgumentException("Cookie with name " + name + " does not exist.");
    }

    public static void deleteCookie(HttpServletResponse response, String name, String path) {
        ResponseCookie deletedCookie = ResponseCookie.from(name, "")
                .maxAge(0)
                .path(path)
                .secure(true)
                .httpOnly(true)
                .build();
        response.addHeader("Set-Cookie", deletedCookie.toString());
    }
}