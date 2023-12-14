package com.bit.lot.flower.auth.oauth.util;

import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserInfoCipherHelper {

  private final EncryptionUtil encryptionUtil;

  public String encrpyt(String oauthRedirectURL,
      SocialLoginRequestCommand command) throws Exception {

    StringBuilder sb = new StringBuilder();
    sb.append(oauthRedirectURL)
        .append("/")
        .append(encryptionUtil.encrypt(command.getSocialId().toString()))
        .append("/")
        .append(encryptionUtil.encrypt(command.getNickname()))
        .append("/")
        .append(encryptionUtil.encrypt(command.getEmail()))
        .append("/")
        .append(encryptionUtil.encrypt(command.getPhoneNumber()));

    return sb.toString();
  }


  public SocialLoginRequestCommand decrypt(SocialLoginRequestCommand command) throws Exception {
      String phoneNumber =encryptionUtil.decrypt(command.getPhoneNumber());
      String email = encryptionUtil.decrypt(command.getEmail());
      String socialId = encryptionUtil.decrypt(command.getSocialId().getValue().toString());
      String nickname = encryptionUtil.decrypt(command.getNickname());
      return createDecryptDto(phoneNumber, email, new AuthId(Long.valueOf(socialId)), nickname);
    }


  private SocialLoginRequestCommand createDecryptDto(String phoneNumber, String email,
      AuthId socialId, String nickName) {
    return SocialLoginRequestCommand.builder().phoneNumber(phoneNumber)
        .socialId(socialId).nickname(nickName).email(
            email).build();
  }
}