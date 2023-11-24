package com.bit.lot.flower.auth.system.filter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.bit.lot.flower.auth.common.util.CookieUtil;
import com.bit.lot.flower.auth.common.util.RedisRefreshTokenUtil;
import com.bit.lot.flower.auth.common.valueobject.SecurityPolicyStaticValue;
import com.bit.lot.flower.auth.system.admin.dto.SystemAdminLoginDto;
import com.bit.lot.flower.auth.system.admin.exception.SystemAdminAuthException;
import com.bit.lot.flower.auth.system.admin.filter.SystemAdminAuthenticationFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
  @Value("${cookie.refresh.token.name}")
  String refreshTokenName;

  @Autowired
  SystemAdminAuthenticationFilter authenticationFilter;
  @Autowired
  private WebApplicationContext webApplicationContext;
  @Autowired
  RedisRefreshTokenUtil redisRefreshTokenUtil;

  MockMvc mvc;


  @BeforeEach
  public void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(authenticationFilter)
        .build();
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

  @DisplayName("잘못된 계정으로 로그인 시도")
  @Test
  void Login_WhenIdAndPasswordAreNotMatched_CatchBadCredentialException()
      throws Exception {
    SystemAdminLoginDto dto = createUnValidSystemAdminAccount();
    assertThrows(SystemAdminAuthException.class, () -> {
      getUnValidSystemAdminUserResult(dto);
    });
  }


  @DisplayName("유효한 계정으로 로그인시 JWT토큰 response에서 확인")
  @Test
  void Login_WhenIdAndPasswordAreNotMatched_JwtTokenInResponse() throws Exception {
    SystemAdminLoginDto validDto = createValidSystemAdminAccount();
    MvcResult validUser = getValidSystemAdminUser(validDto);
    MockHttpServletResponse response = validUser.getResponse();
    System.out.println("response:{}" + response);
    String authorizationHeader = response.getHeader("Authorization");
    assertNotNull(authorizationHeader);
  }

  @DisplayName("유효한 계정으로 로그인시 RefreshToken Redis에서 존재 확인")
  @Test
  void Login_WhenIdAndPasswordAreNotMatched_RefreshTokenInRedis() throws Exception {
    SystemAdminLoginDto validDto = createValidSystemAdminAccount();
    MvcResult validUser = getValidSystemAdminUser(validDto);
    assertNotNull(redisRefreshTokenUtil.getRefreshToken(validDto.getEmail()));

  }

  @DisplayName("유효한 계정으로 로그인시 RefreshToken Cookie에서 존재 확인")
  @Test
  void Login_WhenIdAndPasswordAreNotMatched_RefreshTokenInResponseCookie() throws Exception {
    SystemAdminLoginDto validDto = createValidSystemAdminAccount();
    MvcResult validUser = getValidSystemAdminUser(validDto);
    assertNotNull(validUser.getResponse().getCookie(refreshTokenName));

  }


}
