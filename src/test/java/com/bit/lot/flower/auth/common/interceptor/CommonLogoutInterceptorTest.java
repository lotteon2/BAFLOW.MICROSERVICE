package com.bit.lot.flower.auth.common.interceptor;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CommonLogoutInterceptorTest {


  private final String jwtSubject = "1";
  @Autowired
  WebApplicationContext webApplicationContext;
  MockMvc mvc;
  @Autowired
  RedisBlackListTokenUtil redisBlackListTokenUtil;
  @Autowired
  RedisRefreshTokenUtil redisRefreshTokenUtil;

  void setJwtTokenAtRequestHeader() throws Exception {
    String yourHeaderKey = "Authorization";
    String yourHeaderValue = JwtUtil.generateAccessToken(jwtSubject);

    mvc.perform(MockMvcRequestBuilders.post("/api/auth/system/logout")
            .header(yourHeaderKey, yourHeaderValue))
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


  @DisplayName("request에 토큰이 존재하지 않을 때 IllegalArgumentException throw 테스트 ")
  @Test
  void InvalidateToken_WhenThereIsNotTokenAtHeader_ThrowIllegalArgumentException() {
    assertThrowsExactly(IllegalArgumentException.class,()->{
      setWithoutTokenAtRequestHeader();
    });
  }

  @DisplayName("request에 토큰이 존재할 때 Redis BlackList에 토큰 추가 확인 테스트")
  @Test
  void InvalidateToken_WhenThereIsTokenAtHeader_ThereIsTokenAtRedisBlackList() throws Exception {
    setJwtTokenAtRequestHeader();


  }

  @DisplayName("request에 토큰이 존재할 때 refresh-cookie 제거")
  @Test
  void InvalidateToken_WhenThereIsTokenAtHeader_ThereIsNotRefreshTokenAtCookie() {

  }


  @DisplayName("request에 토큰이 존재할 때 Redis Refresh토큰 제거 ")
  @Test
  void InvalidateToken_WhenThereIsTokenAtHeader_ThereIsNotRefreshTokenAtRedisRefreshTokenUtil() {

  }
}

