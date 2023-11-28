package com.bit.lot.flower.auth.store.http.filter;

import com.bit.lot.flower.auth.common.security.TokenHandler;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.store.dto.StoreManagerLoginDto;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class StoreManagerAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager storeManagerAuthenticationManager;


  @Autowired
  public StoreManagerAuthenticationFilter(
      @Qualifier("storeAuthenticationManager") AuthenticationManager storeManagerAuthenticationManager) {
    super(storeManagerAuthenticationManager);
    this.storeManagerAuthenticationManager = storeManagerAuthenticationManager;
  }


  private StoreManagerLoginDto getLoginDtoFromRequest(HttpServletRequest request)
      throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(request.getInputStream(),
        StoreManagerLoginDto.class);
  }


  /**
   * @throws StoreManagerAuthException StoreManager의 Status가 Pending일 경우 StoreMangerAuthException이
   *                                   던져진다.
   */
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) {
    try {
      StoreManagerLoginDto dto = getLoginDtoFromRequest(request);
      return storeManagerAuthenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword(), null));

    } catch (BadCredentialsException | IOException e) {
      throw new BadCredentialsException("존재하지 않는 시스템 어드민 유저입니다.");
    } catch (StoreManagerAuthException e){
      throw new StoreManagerAuthException(e.getMessage());
    }

  }






}
