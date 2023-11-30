package com.bit.lot.flower.auth.store.service;

import com.bit.lot.flower.auth.store.dto.command.StoreMangerSignUpCommand;
import org.springframework.stereotype.Service;

@Service
public interface StoreManagerSingUpService {

  public void singUp(StoreMangerSignUpCommand dto);
}
