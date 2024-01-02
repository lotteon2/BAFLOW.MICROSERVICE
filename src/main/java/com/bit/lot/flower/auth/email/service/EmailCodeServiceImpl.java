package com.bit.lot.flower.auth.email.service;


import com.bit.lot.flower.auth.email.entity.EmailCode;
import com.bit.lot.flower.auth.email.exception.EmailCodeException;
import com.bit.lot.flower.auth.email.repository.EmailCodeJpaRepository;
import com.bit.lot.flower.auth.email.util.EmailVerificationCodeGenerator;
import java.time.ZonedDateTime;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailCodeServiceImpl implements
    EmailCodeService {


  private final EmailCodeVerificationStrategy strategy;
  private final EmailCodeJpaRepository jpaRepository;
  private final MailService sendEmailService;
  private final String SEND_EMAIL_TITLE ="회원가입 인증 이메일";

  @Override
  public void create(String email) {
    String randomEmailCode = EmailVerificationCodeGenerator.generateVerificationCode();
    EmailCode code =
        EmailCode.builder().email(email).emailCode(randomEmailCode
        ).build();
    jpaRepository.save(code);
    sendEmailService.sendEmail(email,SEND_EMAIL_TITLE,randomEmailCode);
  }

  @Transactional
  @Override
  public void verify(String email, String emailCode) {
    EmailCode foundEmailCode = jpaRepository.findByEmailCodeAndEmail(emailCode, email)
        .orElseThrow(() -> {
          throw new EmailCodeException("해당 이메일에 보내진 이메일 코드가 아닙니다.");
        });
    strategy.verify(foundEmailCode);
  }
}
