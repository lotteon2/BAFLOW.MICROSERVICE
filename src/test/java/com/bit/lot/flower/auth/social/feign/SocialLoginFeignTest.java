package com.bit.lot.flower.auth.social.feign;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.bit.lot.flower.auth.social.dto.message.SocialUserLoginDto;
import com.bit.lot.flower.auth.social.dto.response.UserFeignLoginResponse;
import com.bit.lot.flower.auth.social.http.feign.LoginSocialUserFeignRequest;
import com.bit.lot.flower.auth.social.message.CreateSocialUserRequestImpl;
import com.bit.lot.flower.auth.social.valueobject.AuthId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
    SocialUserLoginDto dto = new SocialUserLoginDto(AuthId.builder().value(2L).build(),
        "testNickName", "testEmail");
    UserFeignLoginResponse expectedResponse = new UserFeignLoginResponse("jiung", true);

    ResponseEntity<UserFeignLoginResponse> responseEntity =
        new ResponseEntity<>(expectedResponse, HttpStatus.OK);
    when(feignClient.login(dto)).thenReturn(responseEntity);

    ResponseEntity<UserFeignLoginResponse> actualResponse = loginSocialUserFeignRequest.login(
        dto);
    assertEquals(expectedResponse, actualResponse);
  }}

