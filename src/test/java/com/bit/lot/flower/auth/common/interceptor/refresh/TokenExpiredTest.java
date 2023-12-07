package com.bit.lot.flower.auth.common.interceptor.refresh;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.bit.lot.flower.auth.common.http.interceptor.filter.JwtAuthenticationFilter;
import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.util.RedisBlackListTokenUtil;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import io.jsonwebtoken.ExpiredJwtException;
import javax.crypto.SecretKey;
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
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class TokenExpiredTest {

  @InjectMocks
  JwtAuthenticationFilter jwtAuthenticationFilter;
  @Mock
  RedisBlackListTokenUtil redisBlackListTokenUtil;

  MockHttpServletRequest request;
  MockHttpServletResponse response;
  MockFilterChain filterChain;

  @BeforeEach
  void init() {
    ReflectionTestUtils.setField(JwtUtil.class, "accessSecret", mock(SecretKey.class));
    Mockito.mockStatic(JwtUtil.class);
    Mockito.mockStatic(SecurityPolicyStaticValue.class);
    request = new MockHttpServletRequest();
    request.addHeader(SecurityPolicyStaticValue.TOKEN_AUTHORIZATION_HEADER_NAME,
        SecurityPolicyStaticValue.TOKEN_AUTHORIZATION_PREFIX + "unValid");
    response = new MockHttpServletResponse();
  }

  @Test
  @DisplayName("JWT 토큰이 Expired 되었을 때 에러 Throw")
  void expirationTest_WhenJwtTokenIsExpired_ThrowExpiredJwtException() {

    when(redisBlackListTokenUtil.isTokenBlacklisted(anyString())).thenReturn(false);
    when(JwtUtil.isTokenValid(anyString())).thenThrow(ExpiredJwtException.class);

    assertThrows(ExpiredJwtException.class,
        () -> jwtAuthenticationFilter.doFilterInternal(request, response, filterChain));

  }

}
