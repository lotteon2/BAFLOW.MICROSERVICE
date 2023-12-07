package com.bit.lot.flower.auth.common.http.interceptor.filter;

import com.bit.lot.flower.auth.common.dto.RenewAccessTokenDto;
import com.bit.lot.flower.auth.common.exception.ErrorDTO;
import com.bit.lot.flower.auth.common.util.JsonBinderUtil;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.common.valueobject.AuthId;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (ExpiredJwtException e) {
      JsonBinderUtil.setResponseWithJson(response, 403, createDtoByToken(e));
    } catch (RuntimeException e) {
      ErrorDTO dto = new ErrorDTO(String.valueOf(401), e.getMessage());
      JsonBinderUtil.setResponseWithJson(response, 401, dto);
    }
  }

    private RenewAccessTokenDto<AuthId> createDtoByToken (ExpiredJwtException e){
      return RenewAccessTokenDto.<AuthId>builder()
          .authId(new AuthId(Long.valueOf(e.getClaims().getSubject())))
          .role(Role.valueOf(e.getClaims().get(
              SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME, String.class))).build();
    }
  }

