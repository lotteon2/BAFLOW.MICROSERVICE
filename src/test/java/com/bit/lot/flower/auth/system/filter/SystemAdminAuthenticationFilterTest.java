package com.bit.lot.flower.auth.system.filter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.bit.lot.flower.auth.system.admin.dto.SystemAdminLoginDto;
import com.bit.lot.flower.auth.system.admin.exception.SystemAdminAuthException;
import com.bit.lot.flower.auth.system.admin.filter.SystemAdminAuthenticationFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-local.yml")
class SystemAdminAuthenticationFilterTest {

  @Value("${system.admin.id}")
  String id;
  @Value("${system.admin.password}")
  String password;
  @Autowired
  SystemAdminAuthenticationFilter authenticationFilter;
  @Autowired
  private WebApplicationContext webApplicationContext;

  MockMvc mvc;

  @BeforeEach
  public void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(authenticationFilter).build();
  }

  private String asJsonString(Object obj) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(obj);
  }


  private SystemAdminLoginDto createUnValidSystemAdminAccount() {
    return new SystemAdminLoginDto("unValidId", "unValidPassword");
  }

  private SystemAdminLoginDto createValidSystemAdminAccount() {
    return new SystemAdminLoginDto(id, password);


  }

  private MvcResult getValidSystemAdminUser(SystemAdminLoginDto validUserDto)
      throws Exception {
    return mvc.perform(MockMvcRequestBuilders
            .post("/api/auth/admin/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validUserDto)))
        .andExpect(status().isOk())
        .andReturn();
  }


  private MvcResult getUnValidSystemAdminUserResult(SystemAdminLoginDto unValidDto)
      throws Exception {
    System.out.println("dto:{}" + unValidDto.getEmail());
    return
        mvc.perform(MockMvcRequestBuilders
                .post("/api/auth/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(unValidDto)))
            .andExpect(status().is4xxClientError()).andReturn();

  }

  @Test
  void Login_WhenIdAndPasswordAreNotMatched_CatchBadCredentialException()
      throws Exception {
    SystemAdminLoginDto dto = createUnValidSystemAdminAccount();
    assertThrows(SystemAdminAuthException.class, () -> {
      getUnValidSystemAdminUserResult(dto);
    });
  }


  @Test
  void Login_WhenIdAndPasswordAreNotMatched_JwtTokenInResponse() throws Exception {
    SystemAdminLoginDto validDto = createValidSystemAdminAccount();
    MvcResult validUser = getValidSystemAdminUser(validDto);
    MockHttpServletResponse response = validUser.getResponse();
    System.out.println("response:{}" + response);
    String authorizationHeader = response.getHeader("Authorization");
    assertNotNull(authorizationHeader);
  }

  @Test
  void Login_WhenIdAndPasswordAreNotMatched_RefreshTokenInRedis() {

  }

  @Test
  void Login_WhenIdAndPasswordAreNotMatched_RefreshTokenInResponseCookie() {

  }


}
