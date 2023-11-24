package com.bit.lot.flower.auth.store.http.filter;

import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.store.dto.StoreManagerLoginDto;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.bit.lot.flower.auth.store.security.StoreAuthenticationManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMapping;

public class StoreManagerAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager storeManagerAuthenticationManager;
  private final TokenHandler tokenHandler;

  @Autowired
  public StoreManagerAuthenticationFilter(
      @Qualifier("storeAuthenticationManager") AuthenticationManager storeManagerAuthenticationManager,
      TokenHandler tokenHandler) {
    super(storeManagerAuthenticationManager);
    this.storeManagerAuthenticationManager = storeManagerAuthenticationManager;
    this.tokenHandler = tokenHandler;
  }


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

    } catch (BadCredentialsException | IOException e){
      throw new BadCredentialsException("존재하지 않는 시스템 어드민 유저입니다.");
    }
    catch (StoreManagerAuthException e){
      throw new StoreManagerAuthException(e.getMessage());
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
    String token = tokenHandler.createToken(String.valueOf(authResult.getPrincipal()), claimMap,response);
    response.addHeader(SecurityPolicyStaticValue.TOKEN_AUTHORIZAION_HEADER_NAME,
        SecurityPolicyStaticValue.TOKEN_AUTHORIZATION_PREFIX + token);
  }



}
