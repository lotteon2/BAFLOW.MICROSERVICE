package com.bit.lot.flower.auth.email.service;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public interface EmailCodeService {
  void create(String email);
  void verify(String email,String emailCode);
}
