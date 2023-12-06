package com.bit.lot.flower.auth.common.security;

import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import java.io.IOException;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SystemAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final TokenHandler tokenHandler;


  private String getRoleFromSecurityContext() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && !authentication.getAuthorities().isEmpty()) {
      return authentication.getAuthorities().iterator().next().getAuthority();
    }
    throw new IllegalArgumentException("토큰에 해당 유저의 역할이 담겨있지 않습니다.");
  }

  private Map<String, Object> createClaimsRoleMap() {
    return JwtUtil.addClaims(
        SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME, getRoleFromSecurityContext());
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authentication) throws IOException, ServletException {
    onAuthenticationSuccess(request, response, authentication);
    chain.doFilter(request,response);
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    String token = tokenHandler.createToken(getIdFromPrincipal(authentication),
        createClaimsRoleMap(), response);
    response.setHeader(SecurityPolicyStaticValue.TOKEN_AUTHORIZAION_HEADER_NAME,SecurityPolicyStaticValue.TOKEN_AUTHORIZATION_PREFIX +token );
  }

  private String getIdFromPrincipal(Authentication authentication){
    return ((AuthId)authentication.getPrincipal()).getValue().toString();
  }
}
