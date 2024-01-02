package com.bit.lot.flower.auth.store.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailDuplicationCheckerService {
  public void isDuplicated(String email);
}
