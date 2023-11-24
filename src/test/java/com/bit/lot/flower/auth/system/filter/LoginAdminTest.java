package com.bit.lot.flower.auth.system.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;import static org.mockito.ArgumentMatchers.anyString;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bit.lot.flower.auth.system.admin.dto.SystemAdminLoginDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class LoginAdminTest {


  @Value("{system.admin.id}")
  private String id;
  @Value("{system.admin.password}")
  private String password;
  @Autowired
  private MockMvc mvc;


  private String asJsonString(Object obj) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(obj);
  }

  @Test
  void Login_WhenIdAndPasswordMatched_GetResponse200AndAuthorizationHeader() throws Exception {
    SystemAdminLoginDto loginDto = new SystemAdminLoginDto(id, password);

    MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/api/auth/admin/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(loginDto)))
        .andExpect(status().isOk())
        .andReturn();
    MockHttpServletResponse response = result.getResponse();
    String authorizationHeader = response.getHeader("Authorization");

    assertNotNull(authorizationHeader);

  }

  @Test
  void Login_WhenIdAndPasswordMatched_RefreshTokenIsInRedis() {

  }

  @Test
  void Login_WhenIdAndPasswordAreNotMatched_GetResponse401NoAuthorizationHeader() {

  }


}
