package com.bit.lot.flower.auth.email.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import com.bit.lot.flower.auth.email.entity.EmailCode;
import com.bit.lot.flower.auth.email.exception.EmailCodeException;
import com.bit.lot.flower.auth.email.repository.EmailCodeJpaRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@TestPropertySource(locations="classpath:application-test.yml")
@ActiveProfiles("test")
@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
 class VerifyEmailServiceTest {

  private final String testEmail = "rnwldnd7248@gmail.com";
  @Autowired
  EmailCodeService emailCodeService;
  @Autowired
  EmailCodeJpaRepository repository;

  private void sendFirstEmail() {
    emailCodeService.create(testEmail);
  }

  private void sendSecondEmail() {
    emailCodeService.create(testEmail);
  }


  @DisplayName("가장 최근에 보내진 이메일 랜덤 Value로 인증")
  @Test
  void VerifyEmail_WhenEmailIsLastSent_NotThrowEmailCodeException() {
    sendFirstEmail();
    EmailCode getRecentSentEmailCode = repository.findAll().get(0);
    assertDoesNotThrow(() -> {
      emailCodeService.verify(testEmail, getRecentSentEmailCode.getEmailCode());
    });

  }

  @DisplayName("가장 최근에 보내지지 않은 이메일 랜덤 Value로 인증시 EmailCodeException Throw")
  @Test
  void VerifyEmail_WhenEmailIsNotLastSent_ThrowEmailCodeException() {

    sendFirstEmail();
    sendSecondEmail();
    List<EmailCode> emailList = repository.findAll();
    assertThrowsExactly(EmailCodeException.class, () -> {
      emailCodeService.verify(testEmail, findNotRecentEmailCode(emailList).getEmailCode());
    });
  }

  private EmailCode findNotRecentEmailCode(List<EmailCode> emailCodeList) {
    EmailCode notRecentEmailCode = emailCodeList.get(0);
    if (notRecentEmailCode.getCreatedAt().compareTo(emailCodeList.get(1).getCreatedAt())>0){
      notRecentEmailCode = emailCodeList.get(1);

    }
      return notRecentEmailCode;
  }

}


