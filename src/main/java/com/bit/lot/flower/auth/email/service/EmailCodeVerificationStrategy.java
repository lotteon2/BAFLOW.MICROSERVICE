package com.bit.lot.flower.auth.email.service;

import com.bit.lot.flower.auth.email.entity.EmailCode;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Service;

@Service
public interface EmailCodeVerificationStrategy {

  public void verify(EmailCode targetEmailCode);
}
