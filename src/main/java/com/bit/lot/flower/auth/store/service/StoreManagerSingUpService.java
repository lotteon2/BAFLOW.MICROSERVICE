package com.bit.lot.flower.auth.store.service;

import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpDto;
import org.springframework.stereotype.Service;

@Service
public interface StoreManagerSingUpService {

  public void singUp(StoreMangerSignUpDto dto);
}
