package com.bit.lot.flower.auth.system.filter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.system.admin.filter.SystemAdminAuthorizationFilter;
import io.jsonwebtoken.MalformedJwtException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SystemAdminAuthorizationFilterTest {

  @Autowired
  private SystemAdminAuthorizationFilter authorizationFilter;
  @Autowired
  private WebApplicationContext webApplicationContext;
  private MockMvc mvc;


  String testUserId = "id";

  private String unValidToken() {
    return "unValidRandomToken";
  }

  private String validToken() {
    Map<String, Object> claimMap = JwtUtil.addClaims(SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME,
        Role.ROLE_SYSTEM_ADMIN.name());
    return JwtUtil.generateAccessTokenWithClaims(testUserId, claimMap);
  }


  private MvcResult requestWithValidToken()
      throws Exception {

    return mvc.perform(MockMvcRequestBuilders.post("/api/auth/admin/logout")
            .header("Authorization", "Bearer " + validToken()))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
  }

  private MvcResult requestWithUnValidToken()
      throws Exception {
    return mvc.perform(MockMvcRequestBuilders.post("/api/auth/admin/logout")
            .header("Authorization", "Bearer " + unValidToken()))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
  }

  private MvcResult requestWithNoTokenAtHeader()
      throws Exception {
    return mvc.perform(MockMvcRequestBuilders.post("/api/auth/admin/logout"))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError())
        .andReturn();
  }

  @BeforeEach
  void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(authorizationFilter)
        .build();
  }


  /**
   * @throws IllegalArgumentException 해당 error를 해당 Filter에서 try catch하지 않는 이유는 해당 error를
   *                                  JwtAuthetnicationFitler에서 잡아주기 때문이다. JwtAuthenticationFilter를
   *                                  거치지 않고 해당 Filter를 거치지 않는 경우는 없다.
   */
  @Test
  void systemAdminTokenAuthorizationTest_WhenTokenIsNotExist_CatchJwtException()
      throws IllegalArgumentException {
    assertThrows(IllegalArgumentException.class, () -> {
      requestWithNoTokenAtHeader();
    });
  }

  /**
   * JwtUtil.generateAccessToken(testUserId) 해당 코드를 작성하는 이유는 accesskey를 등록하기 위해서이다. accesskey를 등록하지
   * 않으면 무조건 IllegalArgumentException이 전파된다.
   */

  @Test
  void systemAdminTokenAuthorizationTest_WhenTokenIsExistAfterLoginAndAccessKeyExist_ThrowMalformedJwtException() {
    JwtUtil.generateAccessToken(testUserId);
    assertThrows(MalformedJwtException.class, () -> {
      requestWithUnValidToken();
    });
  }


  @Test
  void systemAdminTokenAuthorizationTest_WhenTokenIsExistAfterLoginAndGeneratedAccessToken_status200()
      throws Exception {
    requestWithValidToken();
  }

}
