package com.bit.lot.flower.auth.social.feign;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.bit.lot.flower.auth.social.dto.command.SocialUserLoginCommand;
import com.bit.lot.flower.auth.social.dto.response.UserLoginResponse;
import com.bit.lot.flower.auth.social.http.feign.LoginSocialUserFeignRequest;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@ExtendWith(MockitoExtension.class)
public class SocialLoginFeignTest {

  @Mock
  private LoginSocialUserFeignRequest feignClient;

  @Mock
  private LoginSocialUserFeignRequest loginSocialUserFeignRequest;


  @Test
  void testFeignClient() {
    SocialUserLoginCommand dto = new SocialUserLoginCommand(AuthId.builder().value(2L).build(),
        "testNickName", "testEmail");
    UserLoginResponse expectedResponse = new UserLoginResponse("jiung", true);

    ResponseEntity<UserLoginResponse> responseEntity =
        new ResponseEntity<>(expectedResponse, HttpStatus.OK);
    when(feignClient.login(dto)).thenReturn(responseEntity);

    ResponseEntity<UserLoginResponse> actualResponse = loginSocialUserFeignRequest.login(
        dto);
    assertEquals(expectedResponse, actualResponse);
  }}

