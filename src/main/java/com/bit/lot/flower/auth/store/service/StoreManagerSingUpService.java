package com.bit.lot.flower.auth.store.service;

import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpCommand;
import org.springframework.stereotype.Service;

@Service
public interface StoreManagerSingUpService {
  public Long signup(StoreMangerSignUpCommand dto);
}
