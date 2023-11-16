package com.bit.lot.flower.auth.common.valueobject;

import org.springframework.beans.factory.annotation.Value;

public class SecurityPolicyStaticValue {

  @Value("${jwt.refresh.lifetime}")
  public static String REFRESH_EXPIRATION_TIME;
  public static String USER_ID_HEADER_NAME = "userId";
  public static String TOKEN_AUTHORIZAION_HEADER_NAME = "Authorization";
  public static String CLAIMS_ROLE_KEY_NAME = "ROLE";
  @Value("jwt.access.lifetime")
  public static String ACCESS_EXPIRATION_TIME;


}
