package com.bit.lot.flower.auth.social.filter;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.social.http.filter.SocialAuthorizationFilter;
import io.jsonwebtoken.MalformedJwtException;
import java.util.Map;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
@TestPropertySource(locations="classpath:application-test.yml")
@ActiveProfiles("test")
@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
 class SocialAuthorizationFilterTest {

  @Autowired
  SocialAuthorizationFilter authorizationFilter;
  @Autowired
  WebApplicationContext webApplicationContext;
  MockMvc mvc;

  Long testUserId = 1L;

  @BeforeEach
  void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(authorizationFilter)
        .build();
  }


  private String createUnValidToken() {
    return "unValidRandomToken";
  }


  private MvcResult requestWithUnValidToken()
      throws Exception {
    return mvc.perform(MockMvcRequestBuilders.post("/api/auth/social/{provider}/logout", "kakao")
            .header("Authorization", "Bearer " + createUnValidToken()))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
  }

  private MvcResult requestWithoutTokenAtHeader()
      throws Exception {
    return mvc.perform(MockMvcRequestBuilders.post("/api/auth/social/{provider}/logout", "kakao"))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError())
        .andReturn();
  }


  @DisplayName("헤더에 토큰이 없을 때 Throw IllegalArgumentException 테스트")
  @Test
  void SocialAuthorizationFilterTest_WhenThereIsNotTokenAtHeader_ThrowIllegalArgumentException() {
    JwtUtil.generateAccessToken(testUserId.toString());
    assertThrows(IllegalArgumentException.class, () -> {
      requestWithoutTokenAtHeader();
    });
  }

  @DisplayName("헤더에 토큰이 있지만 ROLE_SOCIAL_USER의 claim을 가지고 있지 않을 때 Throw ThrowMalformedJwtExcpetion 테스트")
  @Test
  void SocialAuthorizationFilterTest_WhenThereIsTokenAtHeaderButNoRole_ThrowMalformedJwtExcpetion() {
    JwtUtil.generateAccessToken(testUserId.toString());
    assertThrows(MalformedJwtException.class, () -> {
      requestWithUnValidToken();
    });
  }



}
