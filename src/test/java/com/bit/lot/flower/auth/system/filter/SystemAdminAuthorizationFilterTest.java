package com.bit.lot.flower.auth.system.filter;

import com.bit.lot.flower.auth.system.admin.filter.SystemAdminAuthorizationFilter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SystemAdminAuthorizationFilterTest {

  @MockBean
  private SystemAdminAuthorizationFilter authorizationFilter;



}
