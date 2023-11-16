package com.bit.lot.flower.auth.system.admin.filter;

import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.system.admin.dto.SystemAdminLoginDto;
import com.bit.lot.flower.auth.system.admin.exception.SystemAdminAuthException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.print.Book;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class SystemAdminAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


  private final AuthenticationManager systemAuthenticationManager;
  private final TokenHandler tokenHandler;


  private SystemAdminLoginDto getLoginDtoFromRequest(HttpServletRequest request)
      throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(request.getInputStream(),
        SystemAdminLoginDto.class);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) {
    try {
      SystemAdminLoginDto dto = getLoginDtoFromRequest(request);
      return systemAuthenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword(),
              Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_SYSTEM_ADMIN.name()))));

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
      Map<String,Object> claimMap = JwtUtil.addClaims(SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME,
        Role.ROLE_SYSTEM_ADMIN.name());
    String token = tokenHandler.createToken(request,claimMap);
    response.addHeader(SecurityPolicyStaticValue.TOKEN_AUTHORIZAION_HEADER_NAME,token);
  }
}

