package com.bit.lot.flower.auth.common.security;

import com.bit.lot.flower.auth.common.util.JwtUtil;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class JwtAccessTokenCreateProcessor {

  private String createAccessTokenWithoutClaims(String subject){
    return JwtUtil.generateAccessToken(subject);
  }
  private String createAccessTokenWithClaims(String subject , Map<String,Object> claimsList){
    return JwtUtil.generateAccessTokenWithClaims(subject,claimsList);
  }

  public String createAccessToken(String subject , Map<String,Object> claimsList){
      if(claimsList==null) return createAccessTokenWithoutClaims(subject);
      else{
        return createAccessTokenWithClaims(subject,claimsList);
      }
  }


}
