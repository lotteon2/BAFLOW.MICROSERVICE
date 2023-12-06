package com.bit.lot.flower.auth.common.http.interceptor.filter;

import com.bit.lot.flower.auth.common.exception.ErrorDTO;
import com.bit.lot.flower.auth.common.util.JsonBinderUtil;
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
    } catch (RuntimeException e) {
      ErrorDTO dto = new ErrorDTO(String.valueOf(401), e.getMessage());
      JsonBinderUtil.setResponseWithJson(response, 401, dto);
    }
  }


}
