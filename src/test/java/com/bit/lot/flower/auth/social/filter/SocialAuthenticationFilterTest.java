package com.bit.lot.flower.auth.social.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bit.lot.flower.auth.common.util.RedisRefreshTokenUtil;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.entity.SocialAuth;
import com.bit.lot.flower.auth.social.exception.SocialAuthException;
import com.bit.lot.flower.auth.social.http.filter.SocialAuthenticationFilter;
import com.bit.lot.flower.auth.social.repository.SocialAuthJpaRepository;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import com.bit.lot.flower.auth.store.dto.StoreManagerLoginDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZonedDateTime;
import javax.swing.Spring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SocialAuthenticationFilterTest {

  @Value("${cookie.refresh.token.name}")
  String refreshTokenName;
  @Value("${security.authorization.header.name}")
  String authorizationHeaderName;
  @Autowired
  SocialAuthenticationFilter socialAuthenticationFilter;
  @Autowired
  RedisRefreshTokenUtil redisRefreshTokenUtil;
  @Autowired
  SocialAuthJpaRepository repository;
  @Autowired
  WebApplicationContext webApplicationContext;

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

  private SocialLoginRequestCommand getSocialLoginRequestCommand() {
    return SocialLoginRequestCommand.builder().socialId(getAuthIdFromValue(1L))
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
    SocialLoginRequestCommand command = getSocialLoginRequestCommand();
    socialUserLoginRequest(command);
    assertTrue(repository.findById(command.getSocialId().getValue()).isPresent());
  }

  @DisplayName("유저가 존재하지 않을 때 소셜 자동 회원가입 후 JWT토큰 확인")
  @Test
  void socialUserLoginTest_WhenUserIsNotExist_JWTTokenInResponse() throws Exception {
    SocialLoginRequestCommand command = getSocialLoginRequestCommand();
    MvcResult resultWithJwtToken = socialUserLoginRequest(command);
    assertNotNull(
        resultWithJwtToken.getResponse().getHeader(authorizationHeaderName));
  }


  @DisplayName("유저가 존재하지 않을 때 소셜 자동 회원가입 후 Refresh토큰 Cookie에 담겨있는지 확인")
  @Test
  void socialUserLoginTest_WhenUserIsNotExist_RefreshTokenInCookie() throws Exception {
    SocialLoginRequestCommand command = getSocialLoginRequestCommand();
    MvcResult refreshTokenAtCookieResponse = socialUserLoginRequest(command);
    assertNotNull(refreshTokenAtCookieResponse.getResponse().getCookie(refreshTokenName));

  }

  @DisplayName("유저가 존재하지 않을 때 소셜 자동 회원입 후 Refresh토큰 Redis에 담겨있는지 확인")
  @Test
  void socialUserLoginTest_WhenUserIsNotExist_RefreshInRedis() throws Exception {
    SocialLoginRequestCommand command = getSocialLoginRequestCommand();
    socialUserLoginRequest(command);
    assertNotNull(redisRefreshTokenUtil.getRefreshToken(command.getSocialId().toString()));
  }

  @DisplayName("유저가 존재하고 최근 회원탈퇴를 하지 않은 유저 로그인 성공 테스트")
  @Test
  void socialUserLoginTest_WhenUserIsExistWithNotOutRecentlyStatus_Status200() throws Exception {
    SocialLoginRequestCommand command = getSocialLoginRequestCommand();
    saveStatusNotRecentlyOut(command);
    MvcResult status200Result = socialUserLoginRequest(command);
    assertEquals(200, status200Result.getResponse().getStatus());
  }

  @DisplayName("유저가 존재하고 최근 회원탈퇴를 한 후 24시간이 지나지 않은 회원 에러 확인 테스트")
  @Test
  void socialUserLoginTest_WhenUserIsExistWithOutRecentlyStatusAndTheTimeIsNotPassed24H_ThrowSocialAuthException()
      throws Exception {
    SocialLoginRequestCommand command = getSocialLoginRequestCommand();
    saveStatusRecentlyOutNotPassedOneDay(command);
    assertThrowsExactly(SocialAuthException.class, ()->{
      socialUserLoginRequest(command);
    });

  }

  @DisplayName("유저가 존재하고 최근 회원탈퇴를 한 후 24시간이 지난 회원 isRecentlyOut 상태 false 변경")
  @Test
  void socialUserLoginTest_WhenUserIsExistWithOutRecentlyStatusAndTheTimeIsPassed24H_UserRecentlyStatusToFalse() {

  }


  @DisplayName("유저가 존재하고 최근 회원탈퇴를 한 후 24시간이 지난 회원 로그인 성공 status 200")
  @Test
  void socialUserLoginTest_WhenUserIsExistWithOutRecentlyStatusAndTheTimeIsPassed24H_Status() {

  }

  @DisplayName("유저가 존재하고 최근 회원탈퇴를 한 후 24시간이 지난 회원 로그인 성공후 header에 JWT토큰 존재")
  @Test
  void socialUserLoginTest_WhenUserIsExistWithOutRecentlyStatusAndTheTimeIsPassed24H_JwtTokenIsExistedInHeader() {

  }

}
