package com.bit.lot.flower.auth.store.service;

import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import com.bit.lot.flower.auth.store.repository.StoreManagerAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class EmailDuplicationCheckerServiceImpl implements
    EmailDuplicationCheckerService {

  private final StoreManagerAuthRepository repository;
  @Override
  public void isDuplicated(String email) {
    if(repository.findByEmail(email).isPresent()) throw new StoreManagerAuthException("이미 존재하는 이메일입니다.");
  }
}
