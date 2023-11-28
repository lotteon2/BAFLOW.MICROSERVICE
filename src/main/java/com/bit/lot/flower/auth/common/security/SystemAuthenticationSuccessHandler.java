package com.bit.lot.flower.auth.common.security;

import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SystemAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final TokenHandler tokenHandler;


  private String getRole(Authentication authentication) {
    List<SimpleGrantedAuthority> simpleGrantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities()
        .stream().limit(1).collect(Collectors.toList());
    return simpleGrantedAuthorityList.get(0).getAuthority();
  }

  private String createToken(HttpServletResponse response,
      Authentication authentication) {

    Map<String, Object> claimMap = JwtUtil.addClaims(
        SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME,
        getRole(authentication));
    return tokenHandler.createToken(String.valueOf(authentication.getPrincipal()),
        claimMap, response);
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    createToken(response, authentication);
  }
}
