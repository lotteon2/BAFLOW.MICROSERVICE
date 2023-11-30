package com.bit.lot.flower.auth.social.feign;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bit.lot.flower.auth.common.util.RedisRefreshTokenUtil;
import com.bit.lot.flower.auth.social.dto.command.SocialLoginRequestCommand;
import com.bit.lot.flower.auth.social.http.feign.LoginSocialUserFeignRequest;
import com.bit.lot.flower.auth.social.http.filter.SocialAuthenticationFilter;
import com.bit.lot.flower.auth.social.message.CreateSocialUserRequestImpl;
import com.bit.lot.flower.auth.social.message.LoginSocialUserRequest;
import com.bit.lot.flower.auth.social.repository.SocialAuthJpaRepository;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



@ExtendWith(MockitoExtension.class)
public class SocialLoginFeignTest {

  @Mock
  private CreateSocialUserRequestImpl createSocialUserRequest;
  @InjectMocks
  LoginSocialUserFeignRequest SocialFeignRequest;



  @Test
  void testFeign(){

  }

}
