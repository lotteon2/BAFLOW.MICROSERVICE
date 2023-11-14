package com.bit.lot.flower.auth.email.service;

import com.bit.lot.flower.auth.email.entity.EmailCode;
import com.bit.lot.flower.auth.email.exception.EmailCodeException;
import com.bit.lot.flower.auth.email.repository.EmailCodeJpaRepository;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OnlyRecentEmailCodeIsPermitted implements
    EmailCodeVerificationStrategy {

  private final EmailCodeJpaRepository repository;

  @Override
  public void verify(EmailCode targetEmailCode) {
    List<EmailCode> emailCodeList = repository.findAllByEmail(targetEmailCode.getEmail());
    EmailCode foundRecentEmailCode =findRecent(emailCodeList);
    if(targetEmailCode!=foundRecentEmailCode){
      throw new EmailCodeException("가장 최근에 보내진 이메일 코드로 인증하십시오");
    }

  }

  private EmailCode findRecent(List<EmailCode> emailCodeList) {
    if (emailCodeList == null || emailCodeList.isEmpty()) {
      throw new EmailCodeException("해당 이메일에 이메일 코드가 보내진 적이 없습니다.");
    }

    return Collections.max(emailCodeList, Comparator.comparing(EmailCode::getCreatedAt));
  }
}
