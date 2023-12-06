package com.bit.lot.flower.auth.system.admin.http.filter;

import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.system.admin.dto.SystemAdminLoginDto;
import com.bit.lot.flower.auth.system.admin.exception.SystemAdminAuthException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
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



  @Autowired
  public SystemAdminAuthenticationFilter(
      @Qualifier("systemAuthenticationManager") AuthenticationManager systemAuthenticationManager) {
    super(systemAuthenticationManager);
    this.systemAuthenticationManager = systemAuthenticationManager;
  }

  @Override
  @Autowired
  public void setAuthenticationManager(
      @Qualifier("systemAuthenticationManager") AuthenticationManager authenticationManager) {
    super.setAuthenticationManager(systemAuthenticationManager);
  }


  private SystemAdminLoginDto getLoginDtoFromRequest(HttpServletRequest request)
      throws IOException {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(request.getInputStream(),
          SystemAdminLoginDto.class);
    } catch (IOException e) {
      throw new SystemAdminAuthException("잘못된 입력입니다. 다시 입력해주세요.");
    }
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) {
    try {
      SystemAdminLoginDto dto = getLoginDtoFromRequest(request);
      return systemAuthenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(dto.getId(), dto.getPassword(),
              Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_SYSTEM_ADMIN.name()))));

    } catch (AuthenticationException | IOException e) {
      throw new SystemAdminAuthException("존재하지 않는 시스템 어드민 유저입니다.");
    }

  }


}

