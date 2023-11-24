package com.bit.lot.flower.auth.system.admin.http.filter;

import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SystemAdminAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager systemAuthenticationManager;
  private final TokenHandler tokenHandler;



  @Autowired
  public SystemAdminAuthenticationFilter(
      @Qualifier("systemAuthenticationManager") AuthenticationManager systemAuthenticationManager,
      TokenHandler tokenHandler) {
    super(systemAuthenticationManager);
    this.systemAuthenticationManager = systemAuthenticationManager;
    this.tokenHandler = tokenHandler;
    setFilterProcessesUrl("/api/auth/admin/login");
  }

  @Override
  @Autowired
  public void setAuthenticationManager(
      @Qualifier("systemAuthenticationManager") AuthenticationManager authenticationManager) {
    super.setAuthenticationManager(systemAuthenticationManager);
  }


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
    String token = tokenHandler.createToken(String.valueOf(authResult.getPrincipal()),claimMap,response);
    response.addHeader(SecurityPolicyStaticValue.TOKEN_AUTHORIZAION_HEADER_NAME,SecurityPolicyStaticValue.TOKEN_AUTHORIZATION_PREFIX + token);

  }
}

