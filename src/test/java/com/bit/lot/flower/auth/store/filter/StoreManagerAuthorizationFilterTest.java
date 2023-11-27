package com.bit.lot.flower.auth.store.filter;

import com.bit.lot.flower.auth.common.util.JwtUtil;
import com.bit.lot.flower.auth.common.valueobject.Role;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.store.http.filter.StoreMangerAuthorizationFilter;
import com.bit.lot.flower.auth.system.admin.http.filter.SystemAdminAuthorizationFilter;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StoreManagerAuthorizationFilterTest {


  @Autowired
  StoreMangerAuthorizationFilter authorizationFilter;
  @Autowired
  WebApplicationContext webApplicationContext;
  MockMvc mvc;

   String testUserId = "id";

  private String createUnValidToken() {
    return "unValidRandomToken";
  }

  private String createValidToken() {
    Map<String, Object> claimMap = JwtUtil.addClaims(SecurityPolicyStaticValue.CLAIMS_ROLE_KEY_NAME,
        Role.S.name());
    return JwtUtil.generateAccessTokenWithClaims(testUserId, claimMap);
  }
  private MvcResult requestWithValidToken()
      throws Exception {
    return mvc.perform(MockMvcRequestBuilders.post("/api/auth/stores/logout")
            .header("Authorization", "Bearer " + createValidToken()))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
  }

  private MvcResult requestWithUnValidToken()
      throws Exception {
    return mvc.perform(MockMvcRequestBuilders.post("/api/auth/stores/logout")
            .header("Authorization", "Bearer " + createUnValidToken()))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
  }


  @DisplayName("스토어매니저 상태 Pending일 경우 status 401")
  @Test
  void Access_WhenStoreManagerUserIsPending_Status401() {

  }

  @DisplayName("스토어매니저 상태 Denined일 경우 status 403")
  @Test
  void Access_WhenStoreManagerUserIsDenied_Status403() {

  }

  @DisplayName("스토어매니저 상태 Permitted일 경우 status 403")
  @Test
  void Access_WhenStoreManagerUserIsPermitted_Status200() {

  }


}}
