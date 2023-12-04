package com.bit.lot.flower.auth.social.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bit.lot.flower.auth.common.util.RedisRefreshTokenUtil;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.dto.response.UserFeignLoginResponse;
import com.bit.lot.flower.auth.social.entity.SocialAuth;
import com.bit.lot.flower.auth.social.exception.SocialAuthException;
import com.bit.lot.flower.auth.social.http.filter.SocialAuthenticationFilter;
import com.bit.lot.flower.auth.social.message.LoginSocialUserRequest;
import com.bit.lot.flower.auth.social.repository.SocialAuthJpaRepository;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZonedDateTime;
import java.util.Arrays;
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
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class SocialLoginMvcTest {

  Long testId = 1L;
  @Value("${cookie.refresh.token.name}")
  String refreshTokenName;
  @Value("${security.authorization.header.name}")
  String authorizationHeaderName;
  String refreshTokenLifeTime = "360000";
  @MockBean
  LoginSocialUserRequest loginSocialUserRequest;
  @Autowired
  SocialAuthJpaRepository socialAuthJpaRepository;
  @Autowired
  SocialAuthenticationFilter socialAuthenticationFilter;
  @Autowired
  SocialAuthJpaRepository repository;
  @Autowired
  WebApplicationContext webApplicationContext;
  @MockBean
  RedisTemplate<Object, Object> redisTemplate;
  @MockBean
  RedisRefreshTokenUtil redisRefreshTokenUtil;
  @MockBean
  RedisKeyValueAdapter keyValueAdapter;

  MockMvc mvc;


  @BeforeEach
  public void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .addFilter(socialAuthenticationFilter)
        .build();
  }

  private AuthId getAuthIdFromValue(Long value) {
    return AuthId.builder().value(value).build();
  }

  private SocialLoginRequestCommand getSocialLoginRequestCommand(Long value) {
    return SocialLoginRequestCommand.builder().socialId(getAuthIdFromValue(value))
        .email("test@gmail.com").nickname("testNickName").build();
  }

  private void saveStatusNotRecentlyOut(SocialLoginRequestCommand command) {
    repository.save(new SocialAuth(command.getSocialId().getValue(), false, null));
  }


  private void saveStatusRecentlyOutNotPassedOneDay(SocialLoginRequestCommand command) {
    repository.save(new SocialAuth(command.getSocialId().getValue(), true,
        ZonedDateTime.now().minusHours(23L)));
  }

  private void saveStatusRecentlyOutPassedOneDay(SocialLoginRequestCommand command) {
    repository.save(new SocialAuth(command.getSocialId().getValue(), true,
        ZonedDateTime.now().minusHours(25L)));
  }

  private String asJsonString(Object obj) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(obj);
  }


  private MvcResult socialUserLoginRequest(SocialLoginRequestCommand command)
      throws Exception {
    return mvc.perform(MockMvcRequestBuilders
            .post("/api/auth/social/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(command)))
        .andExpect(status().is2xxSuccessful())
        .andReturn();
  }

  @DisplayName("유저가 존재하지 않을 때 소셜 자동 회원가입 확인")
  @Test
  void socialUserLoginTest_WhenUserIsNotExist_UserIsCreated() throws Exception {

    SocialLoginRequestCommand command = getSocialLoginRequestCommand(testId);
    when(loginSocialUserRequest.request(command)).thenReturn(
        mock(UserFeignLoginResponse.class));

    socialUserLoginRequest(command);
    assertTrue(repository.findById(command.getSocialId().getValue()).isPresent());

  }

  @DisplayName("유저가 존재하지 않을 때 소셜 자동 회원가입 후 JWT토큰 확인")
  @Test
  void socialUserLoginTest_WhenUserIsNotExist_JWTTokenInResponse() throws Exception {
    SocialLoginRequestCommand command = getSocialLoginRequestCommand(testId);
    when(loginSocialUserRequest.request(command)).thenReturn(
        mock(UserFeignLoginResponse.class));
    MvcResult resultWithJwtToken = socialUserLoginRequest(command);
    assertNotNull(
        resultWithJwtToken.getResponse().getHeader(authorizationHeaderName));
  }


  @DisplayName("유저가 존재하지 않을 때 소셜 자동 회원가입 후 Refresh토큰 Cookie에 담겨있는지 확인")
  @Test
  void socialUserLoginTest_WhenUserIsNotExist_RefreshTokenInCookie() throws Exception {
    SocialLoginRequestCommand command = getSocialLoginRequestCommand(testId);
    when(loginSocialUserRequest.request(command)).thenReturn(
        mock(UserFeignLoginResponse.class));
    MvcResult refreshTokenAtCookieResponse = socialUserLoginRequest(command);
    assertNotNull(refreshTokenAtCookieResponse.getResponse().getCookie(refreshTokenName));

  }

  @DisplayName("유저가 존재하지 않을 때 소셜 자동 회원가입 후 Refresh토큰 Redis에 담겨있는지 확인")
  @Test
  void socialUserLoginTest_WhenUserIsNotExist_RefreshInRedis() throws Exception {
    String refreshToken = "dummyRefreshToken";

    Mockito.doNothing().when(redisRefreshTokenUtil)
        .saveRefreshToken(String.valueOf(testId), refreshToken,
            Long.parseLong(refreshTokenLifeTime));

    SocialLoginRequestCommand command = getSocialLoginRequestCommand(testId);
    when(loginSocialUserRequest.request(command)).thenReturn(
        mock(UserFeignLoginResponse.class));
    socialUserLoginRequest(command);

    verify(redisRefreshTokenUtil).saveRefreshToken(
        String.valueOf(testId), refreshToken, Long.parseLong(refreshTokenLifeTime));

    assertNotNull(redisRefreshTokenUtil.getRefreshToken(command.getSocialId().toString()));
  }

  @DisplayName("유저가 존재하고 최근 회원탈퇴를 하지 않은 유저 로그인 성공 테스트")
  @Test
  void socialUserLoginTest_WhenUserIsExistWithNotOutRecentlyStatus_Status200() throws Exception {
    SocialLoginRequestCommand command = getSocialLoginRequestCommand(testId);
    when(loginSocialUserRequest.request(command)).thenReturn(
        mock(UserFeignLoginResponse.class));
    saveStatusNotRecentlyOut(command);
    MvcResult status200Result = socialUserLoginRequest(command);
    assertEquals(200, status200Result.getResponse().getStatus());
  }

  @DisplayName("유저가 존재하고 최근 회원탈퇴를 한 후 24시간이 지나지 않은 회원 에러 확인 테스트")
  @Test
  void socialUserLoginTest_WhenUserIsExistWithOutRecentlyStatusAndTheTimeIsNotPassed24H_ThrowSocialAuthException()
      throws Exception {
    SocialLoginRequestCommand command = getSocialLoginRequestCommand(testId);
    when(loginSocialUserRequest.request(command)).thenReturn(
        mock(UserFeignLoginResponse.class));
    saveStatusRecentlyOutNotPassedOneDay(command);
    assertThrowsExactly(SocialAuthException.class, () -> {
      socialUserLoginRequest(command);
    });

  }

  @DisplayName("유저가 존재하고 최근 회원탈퇴를 한 후 24시간이 지난 회원 isRecentlyOut 상태 false 변경")
  @Test
  void socialUserLoginTest_WhenUserIsExistWithOutRecentlyStatusAndTheTimeIsPassed24H_UserRecentlyStatusToFalse()
      throws Exception {
    SocialLoginRequestCommand command = getSocialLoginRequestCommand(testId);
    when(loginSocialUserRequest.request(command)).thenReturn(
        mock(UserFeignLoginResponse.class));
    saveStatusRecentlyOutPassedOneDay(command);
    socialUserLoginRequest(command);
    assertTrue(repository.findById(command.getSocialId().getValue()).get().isRecentlyOut());

  }


  @DisplayName("유저가 존재하고 최근 회원탈퇴를 한 후 24시간이 지난 회원 로그인 성공 status 200")
  @Test
  void socialUserLoginTest_WhenUserIsExistWithOutRecentlyStatusAndTheTimeIsPassed24H_Status()
      throws Exception {
    SocialLoginRequestCommand command = getSocialLoginRequestCommand(testId);
    when(loginSocialUserRequest.request(command)).thenReturn(
        mock(UserFeignLoginResponse.class));
    saveStatusRecentlyOutPassedOneDay(command);
    socialUserLoginRequest(command);
  }


  /**
   *
   * 이후 JWT토큰과 Redis, Coookie는 모든 같은 클래스 메소드 내에서 실행되는 로직이라
   * 위의 테스트 코드와 같다.
   */
  @DisplayName("유저가 존재하고 최근 회원탈퇴를 한 후 24시간이 지난 회원 로그인 성공후 header에 JWT토큰 존재")
  @Test
  void socialUserLoginTest_WhenUserIsExistWithOutRecentlyStatusAndTheTimeIsPassed24H_JwtTokenIsExistedInHeader()
      throws Exception {
    SocialLoginRequestCommand command = getSocialLoginRequestCommand(testId);
      when(loginSocialUserRequest.request(command)).thenReturn(
        mock(UserFeignLoginResponse.class));
    saveStatusRecentlyOutPassedOneDay(command);
    MvcResult resultWithJwtToken = socialUserLoginRequest(command);
    assertNotNull(
        resultWithJwtToken.getResponse().getHeader(authorizationHeaderName));
  }

}
