package com.bit.lot.flower.auth.email.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@TestPropertySource(locations="classpath:application-test.yml")
@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SendEmailServiceTest {

  private final String to = "rnwldnd7248@gmail.com";
  private final String title = "Test email send";
  private final String text = "test text";
  @Autowired
  MailService mailService;

  @DisplayName("이메일 보내기 테스트")
  @Test
  void sendEmail_WhenToEmailAndTitleAndTextAreProvided_NoException() {
    assertDoesNotThrow(()->{
      mailService.sendEmail(to,title,text);
    });
  }


}
