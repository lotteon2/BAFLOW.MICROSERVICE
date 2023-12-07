package com.bit.lot.flower.auth.system.filter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.bit.lot.flower.auth.common.util.RedisRefreshTokenUtil;
import com.bit.lot.flower.auth.system.admin.dto.SystemAdminLoginDto;
import com.bit.lot.flower.auth.system.admin.exception.SystemAdminAuthException;
import com.bit.lot.flower.auth.system.admin.http.filter.SystemAdminAuthenticationFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@TestPropertySource(locations="classpath:application-test.yml")
@ActiveProfiles("test")
@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class SystemAdminLoginMvcTest {

  @Value("${system.admin.id}")
  Long id;
  @Value("${system.admin.password}")
  String password;
  Long unValidId = 1000L;
  String unValidPassword = "unValidPassword";
  @Value("${cookie.refresh.token.name}")
  String refreshTokenName;

  @Autowired
  SystemAdminAuthenticationFilter authenticationFilter;
  @Autowired
  private WebApplicationContext webApplicationContext;
  @MockBean
  RedisTemplate<Object, Object> redisTemplate;
  @MockBean
  RedisRefreshTokenUtil redisRefreshTokenUtil;
  @MockBean
  RedisKeyValueAdapter keyValueAdapter;

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
    return new SystemAdminLoginDto(unValidId, unValidPassword);
  }

  private SystemAdminLoginDto createValidSystemAdminAccount() {
    return new SystemAdminLoginDto(id, password);


  }

  private MvcResult getValidSystemAdminUserResponse(SystemAdminLoginDto validUserDto)
      throws Exception {
    return mvc.perform(MockMvcRequestBuilders
            .post("/admin/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validUserDto)))
        .andExpect(status().isOk())
        .andReturn();
  }


  private void getUnValidSystemAdminUserResult(SystemAdminLoginDto unValidDto)
      throws Exception {
    System.out.println("dto:{}" + unValidDto.getId());
    mvc.perform(MockMvcRequestBuilders
            .post("/admin/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(unValidDto)))
        .andExpect(status().is4xxClientError()).andReturn();

  }

  @DisplayName("잘못된 계정으로 로그인 시도")
  @Test
  void Login_WhenIdAndPasswordAreNotMatched_CatchBadCredentialException()
       {
    SystemAdminLoginDto dto = createUnValidSystemAdminAccount();
    assertThrows(SystemAdminAuthException.class, () -> {
      getUnValidSystemAdminUserResult(dto);
    });
  }


  @DisplayName("유효한 계정으로 로그인시 JWT토큰 response에서 확인")
  @Test
  void Login_WhenIdAndPasswordAreNotMatched_JwtTokenInResponse() throws Exception {
    SystemAdminLoginDto validDto = createValidSystemAdminAccount();
    MvcResult validUser = getValidSystemAdminUserResponse(validDto);
    MockHttpServletResponse response = validUser.getResponse();
    System.out.println("response:{}" + response);
    String authorizationHeader = response.getHeader("Authorization");
    assertNotNull(authorizationHeader);
  }

  @DisplayName("유효한 계정으로 로그인시 RefreshToken Redis에서 존재 확인")
  @Test
  void Login_WhenIdAndPasswordAreNotMatched_RefreshTokenInRedis() throws Exception {
    SystemAdminLoginDto validDto = createValidSystemAdminAccount();

    Mockito.doNothing()
        .when(redisRefreshTokenUtil).saveRefreshToken("100",null, 3660L);

    getValidSystemAdminUserResponse(validDto);

  }

  @DisplayName("유효한 계정으로 로그인시 RefreshToken Cookie에서 존재 확인")
  @Test
  void Login_WhenIdAndPasswordAreNotMatched_RefreshTokenInResponseCookie() throws Exception {
    SystemAdminLoginDto validDto = createValidSystemAdminAccount();
    MvcResult validUser = getValidSystemAdminUserResponse(validDto);
    assertNotNull(validUser.getResponse().getCookie(refreshTokenName));

  }


}
