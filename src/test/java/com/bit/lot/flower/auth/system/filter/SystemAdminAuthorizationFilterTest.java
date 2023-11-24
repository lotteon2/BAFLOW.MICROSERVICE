package com.bit.lot.flower.auth.system.filter;

import com.bit.lot.flower.auth.system.admin.filter.SystemAdminAuthorizationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SystemAdminAuthorizationFilterTest {

  @Autowired
  private SystemAdminAuthorizationFilter authorizationFilter;
  @Autowired
  private WebApplicationContext webApplicationContext;
  private MockMvc mvc;


  @BeforeEach
  void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(authenticationFilter)
        .build();
  }




  @Test
  void systemAdminTokenAuthorizationTest_WhenTokenIsNotExist_Throw() {

  }



    @Test
  void systemAdminTokenAuthorizationTest_WhenTokenIsExist_PassTheFilter() {

  }

}
