package com.bit.lot.flower.auth.common.interceptor;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.util.RedisBlackListTokenUtil;
import com.bit.lot.flower.auth.common.util.RedisRefreshTokenUtil;

import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;


@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class CommonLogoutInterceptorTest {

  final String jwtSubject = "10";
  final String authenticationHeaderPrefix = "Bearer ";
  final String authorizationHeaderName = "Authorization";
  @Value("${cookie.refresh.token.name}")
  String refreshCookieName;
  String token;
  String redisToken;
  @Autowired
  WebApplicationContext webApplicationContext;
  MockMvc mvc;
  @Autowired
  RedisBlackListTokenUtil redisBlackListTokenUtil;
  @Autowired
  RedisRefreshTokenUtil redisRefreshTokenUtil;


  MvcResult setToken() throws Exception {
    token = JwtUtil.generateAccessToken(jwtSubject);
    redisToken = JwtUtil.generateRefreshToken(jwtSubject);
    redisRefreshTokenUtil.saveRefreshToken(jwtSubject,redisToken,1000000L);
    return mvc.perform(MockMvcRequestBuilders.post("/api/auth/admin/logout")
            .header(authorizationHeaderName, authenticationHeaderPrefix + token))
        .andExpect(status().isOk())
        .andReturn();
  }

  void setWithoutTokenAtRequestHeader() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post("/api/auth/admin/logout"));
  }

  @BeforeEach
  void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .build();
  }


  /**
   * 해당 테스트는 JWTAuthenticationFilter가 동작하지 않은 상태의 테스트입니다. 해당 Interceptor의 전제 조건은
   * JWTAuthenticationFilter가 작동한 이후라 토큰이 없다면 JWTAuthenticationFilter에서 에러를 던지게 됩니다. 따라서 현재는
   * NestedServletException를 Servlet API에서 던지게 됩니다.
   */
  @DisplayName("request에 토큰이 존재하지 않을 때 NestedServletException throw 테스트 ")
  @Test
  void InvalidateToken_WhenThereIsNotTokenAtHeaderAndNotFilteredByJWTAuthenticationFilter_ThrowNestedServletException() {
    assertThrowsExactly(NestedServletException.class, () -> {
      setWithoutTokenAtRequestHeader();
    });
  }

  @DisplayName("request에 토큰이 존재할 때 Redis BlackList에 토큰 추가 확인 테스트")
  @Test
  void InvalidateToken_WhenThereIsTokenAtHeader_ThereIsTokenAtRedisBlackList() throws Exception {
    setToken();
    assertTrue(redisBlackListTokenUtil.isTokenBlacklisted(token));

  }

  @DisplayName("request에 토큰이 존재할 때 refresh-cookie 제거")
  @Test
  void InvalidateToken_WhenThereIsTokenAtHeader_ThereIsNotRefreshTokenAtCookie() throws Exception {
    MvcResult response = setToken();
    assertNull(response.getResponse().getCookie(refreshCookieName));
  }

  @DisplayName("request에 토큰이 존재할 때 Redis Refresh토큰 제거 ")
  @Test
  void InvalidateToken_WhenThereIsTokenAtHeader_ThereIsNotRefreshTokenAtRedisRefreshTokenUtil()
      throws Exception {
    setToken();
    assertNull(redisRefreshTokenUtil.getRefreshToken(redisToken));
  }
}

