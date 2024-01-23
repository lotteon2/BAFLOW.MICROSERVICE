package com.bit.lot.flower.auth.store.service;

import org.springframework.stereotype.Service;

@Service
public interface RequestBusinessNumberFromImageService {

  public String getBusinessNumber(String imageUrl);
}
