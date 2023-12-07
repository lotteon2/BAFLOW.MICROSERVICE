package com.bit.lot.flower.auth.common.interceptor.refresh;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.bit.lot.flower.auth.common.http.interceptor.filter.ExceptionHandlerFilter;
import com.bit.lot.flower.auth.common.http.interceptor.filter.JwtAuthenticationFilter;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.util.RedisBlackListTokenUtil;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class GetUserDataWhenTokenIsExpiredTest {

  @Mock
  ExceptionHandlerFilter exceptionHandlerFilter;

  MockHttpServletRequest request;
  MockHttpServletResponse response;
  MockFilterChain filterChain;

  @BeforeEach
  void init() {
    request = new MockHttpServletRequest();
    response = new MockHttpServletResponse();
    filterChain = new MockFilterChain();
  }


  @Test
  @DisplayName("JWT 토큰이 Expired 되었을 때 Response에 RenewAccessTokenDto 확인")
  void GetUserTokenInfo_WhenTokenIsExpired_ReponseWithRenewAccessTokenDto()
      throws ServletException, IOException {

    when(exceptionHandlerFilter.doFilterInternal(request, response, filterChain)).thenThrow(
        ExpiredJwtException.class);


  }

}
