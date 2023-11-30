package com.bit.lot.flower.auth.store.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.bit.lot.flower.auth.common.util.RedisRefreshTokenUtil;
import com.bit.lot.flower.auth.store.dto.StoreManagerLoginDto;
import com.bit.lot.flower.auth.store.entity.StoreManagerAuth;
import com.bit.lot.flower.auth.store.http.filter.StoreManagerAuthenticationFilter;

import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import com.bit.lot.flower.auth.store.valueobject.StoreManagerStatus;
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
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@TestPropertySource(locations="classpath:application-test.yml")
@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class StoreManagerAuthenticationFilterTest {

  private final String unValidStoreManagerId = "unValidStoreManagerId";
  private final String unValidStoreManagerPassword = "unValidStoreManagerPassword";
  @Value("${store.manager.id}")
  String id;
  @Value("${store.manager.password}")
  String password;
  @Value("${cookie.refresh.token.name}")
  String refreshTokenName;
  @Value("${security.authorization.header.name}")
  String authorizationHeaderName;
  @Autowired
  StoreManagerAuthRepository repository;
  @Autowired
  StoreManagerAuthenticationFilter authenticationFilter;
  @Autowired
  RedisRefreshTokenUtil redisRefreshTokenUtil;
  @Autowired
  BCryptPasswordEncoder encoder;
  @Autowired
  WebApplicationContext webApplicationContext;


  MockMvc mvc;


  @BeforeEach
  public void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(authenticationFilter)
        .build();
  }

  private void saveEncodedPasswordPermittedStoreManager() {
    repository.save(
        StoreManagerAuth.builder().lastLogoutTime(null).password(encoder.encode(password)).email(id)
            .status(
                StoreManagerStatus.ROLE_STORE_MANAGER_PERMITTED).build());
  }


  private void saveEncodedPasswordPendingStoreManager() {
    repository.save(
        StoreManagerAuth.builder().lastLogoutTime(null).password(encoder.encode(password)).email(id)
            .status(
                StoreManagerStatus.ROLE_STORE_MANAGER_PENDING).build());
  }

  private String asJsonString(Object obj) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(obj);
  }


  private StoreManagerLoginDto createValidStoreManagerAccountWithPermittedStatus() {
    return StoreManagerLoginDto.builder().email(id).password(password).build();
  }

  private StoreManagerLoginDto createValidStoreManagerAccountWithPendingStatus() {
    return StoreManagerLoginDto.builder().email(id).password(password).build();
  }

  private StoreManagerLoginDto createIdAndPasswordMistMatchedStoreManagerAccount() {
    return StoreManagerLoginDto.builder().email(unValidStoreManagerId)
        .password(unValidStoreManagerPassword).build();
  }


  private MvcResult getValidStoreManagerResponse(StoreManagerLoginDto validUserDto)
      throws Exception {
    return mvc.perform(MockMvcRequestBuilders
            .post("/api/auth/stores/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(validUserDto)))
        .andExpect(status().is2xxSuccessful())
        .andReturn();
  }

  private MvcResult getUnValidStoreManagerResponse(StoreManagerLoginDto unValidUserDto)
      throws Exception {
    return mvc.perform(MockMvcRequestBuilders
            .post("/api/auth/stores/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(unValidUserDto)))
        .andExpect(status().is4xxClientError())
        .andReturn();
  }


  @DisplayName("스토어매니저 로그인시 JWT토큰 response에 존재 여부 체크 테스트")
  @Test
  void storeManagerLogin_WhenStoreManagerISExist_JWTTokenInResponse() throws Exception {
    saveEncodedPasswordPermittedStoreManager();
    MvcResult validStoreManger = getValidStoreManagerResponse(
        createValidStoreManagerAccountWithPermittedStatus());
    assertNotNull(
        validStoreManger.getResponse().getHeader(authorizationHeaderName));
  }


  @DisplayName("스토어매니저 로그인시 refresh토큰 HttpOnly쿠키에 존재 여부 체크 테스트")
  @Test
  void storeManagerLogin_WhenStoreManagerIsExist_RefreshTokenInHttpOnlyCookie() throws Exception {
    saveEncodedPasswordPermittedStoreManager();
    MvcResult validStoreManager = getValidStoreManagerResponse(
        createValidStoreManagerAccountWithPermittedStatus());
    assertNotNull(validStoreManager.getResponse().getCookie(refreshTokenName));


  }

  @DisplayName("스토어매니저 로그인시 refresh토큰 Redis에 존재 여부 체크 테스트")
  @Test
  void storeManagerLogin_WhenStoreManagerIsExist_RefreshTokenInRedis() throws Exception {
    saveEncodedPasswordPermittedStoreManager();
    String refreshRedisIdName = id;
    MvcResult validStoreManager = getValidStoreManagerResponse(
        createValidStoreManagerAccountWithPermittedStatus());
    assertNotNull(redisRefreshTokenUtil.getRefreshToken(refreshRedisIdName));

  }

  @DisplayName("스토어매니저 상태 Pending일 경우 ThrowStoreMangerAuthException")
  @Test
  void storeManagerLogin_WhenStoreManagerIsExistButStatusIsPending_ThrowStoreMangerAuthException()
      throws Exception {
    saveEncodedPasswordPendingStoreManager();
    assertThrowsExactly(StoreManagerAuthException.class, () -> {
      getUnValidStoreManagerResponse(createValidStoreManagerAccountWithPendingStatus());
    });


  }

  /*
  틀린 계정으로 로그인을 시도하기에 어떤 entity를 저장해도 상관없다.
   */
  @DisplayName("스토어매니저 올바르지 않은 계정으로 로그인시 BadCredentialExceptionThrow 테스트 ")
  @Test
  void storeManagerLogin_WhenStoreManagerIsNotExist_ThrowBadCredentialException() throws Exception {
    saveEncodedPasswordPendingStoreManager();

    assertEquals(401, getUnValidStoreManagerResponse(
        createIdAndPasswordMistMatchedStoreManagerAccount()).getResponse().getStatus());
  }


}
