package com.bit.lot.flower.auth.store.filter;

import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.store.dto.StoreManagerLoginDto;
import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import com.bit.lot.flower.auth.system.admin.dto.SystemAdminLoginDto;
import com.bit.lot.flower.auth.system.admin.exception.SystemAdminAuthException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class StoreManagerAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager storeManagerAuthenticationManager;
  private final TokenHandler tokenHandler;


  private StoreManagerLoginDto getLoginDtoFromRequest(HttpServletRequest request)
      throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(request.getInputStream(),
        StoreManagerLoginDto.class);
  }


  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) {
    try {
      StoreManagerLoginDto dto = getLoginDtoFromRequest(request);
      return storeManagerAuthenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword(),null));

    } catch (AuthenticationException | IOException e) {
      throw new SystemAdminAuthException("존재하지 않는 시스템 어드민 유저입니다.");
    }

  }


  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult) {
    Map<String, Object> claimMap = JwtUtil.addClaims(SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME,
        Role.ROLE_SYSTEM_ADMIN.name());
    String token = tokenHandler.createToken(request, claimMap);
    response.addHeader(SecurityPolicyStaticValue.TOKEN_AUTHORIZAION_HEADER_NAME, token);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException failed) throws IOException, ServletException {
    SecurityContextHolder.clearContext();

  }


}
