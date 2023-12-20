package com.bit.lot.flower.auth.common.valueobject;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityPolicyStaticValue {

  public static String REFRESH_EXPIRATION_TIME;
  public static String ACCESS_EXPIRATION_TIME;
  public static String TOKEN_AUTHORIZATION_HEADER_NAME = "Authorization";
  public static String TOKEN_AUTHORIZATION_PREFIX = "Bearer ";
  public static String CLAIMS_ROLE_KEY_NAME = "ROLE";


  @Value("${refresh.token.lifetime}")
  public void setRefreshExpirationTime(String value) {
    REFRESH_EXPIRATION_TIME = value;
  }

  @Value("${access.token.lifetime}")
  public void setAccessExpirationTime(String value) {
    ACCESS_EXPIRATION_TIME = value;
  }

}
