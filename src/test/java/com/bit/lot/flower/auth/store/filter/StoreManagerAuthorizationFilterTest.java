package com.bit.lot.flower.auth.store.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StoreManagerAuthorizationFilterTest {


  @DisplayName()
  @Test
  void Access_WhenStoreManagerUserIsPending_Status401() {

  }

  @DisplayName()
  @Test
  void Access_WhenStoreManagerUserIsDenied_Status403() {

  }

  @DisplayName()
  @Test
  void Access_WhenStoreManagerUserIsPermitted_Status200() {

  }

  @DisplayName()
  @Test
  void Access_WhenStoreManagerUserIs_Status200() {

  }}
