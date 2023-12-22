package com.bit.lot.flower.auth.store.http.controller;


import bloomingblooms.response.CommonResponse;
import com.bit.lot.flower.auth.common.valueobject.AuthId;
import com.bit.lot.flower.auth.store.dto.StoreManagerLoginResponseWithNameAndStoreId;
import com.bit.lot.flower.auth.store.dto.StoreMangerSignUpCommand;
import com.bit.lot.flower.auth.store.http.message.StoreManagerNameRequest;
import com.bit.lot.flower.auth.store.http.message.StoreManagerStoreIdRequest;
import com.bit.lot.flower.auth.store.mapper.StoreManagerMessageMapper;
import com.bit.lot.flower.auth.store.message.StoreMangerCreateRequest;
import com.bit.lot.flower.auth.store.service.EmailDuplicationCheckerService;
import com.bit.lot.flower.auth.store.service.StoreManagerService;
import com.bit.lot.flower.auth.store.valueobject.StoreId;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class StoreManagerRestController{

  private final StoreManagerNameRequest<AuthId> storeManagerNameRequest;
  private final StoreManagerStoreIdRequest<StoreId,AuthId> storeManagerStoreIdRequest;
  private final EmailDuplicationCheckerService emailDuplicationCheckerService;
  private final StoreManagerService storeManagerService;
  private final StoreMangerCreateRequest storeMangerCreateRequest;


  @PostMapping("/stores/emails/{email}")
  public CommonResponse<String> emailDuplicationCheck(@PathVariable String email) {
    emailDuplicationCheckerService.isDuplicated(email);
    return CommonResponse.success("중복 이메일이 아닙니다.");
  }

  @PostMapping("/stores/signup")
  public CommonResponse<String> signup(@Valid @RequestBody StoreMangerSignUpCommand dto) {
    emailDuplicationCheckerService.isDuplicated(dto.getEmail());
    Long getSignedUpId = storeManagerService.signUp(dto);
    storeMangerCreateRequest.publish(
        StoreManagerMessageMapper.createStoreManagerCommandWithPk(dto, getSignedUpId));
    return CommonResponse.success("스토어 매니저 회원가입 신청 완료 관리자의 승인을 기다려주세요");
  }


  @PostMapping("/stores/login")
  public CommonResponse<StoreManagerLoginResponseWithNameAndStoreId> login(
      @AuthenticationPrincipal AuthId authId) {
    String name = storeManagerNameRequest.getName(authId).getName();
    StoreId storeId = storeManagerStoreIdRequest.requestStoreId(authId);
    if (storeId == null) {
      return CommonResponse.success(
          StoreManagerLoginResponseWithNameAndStoreId.builder().storeId(null).name(name).build());

    } else {
      return CommonResponse.success(StoreManagerMessageMapper.createLoginResponse(name,storeId.getValue()));
    }
  }

  @PostMapping("/stores/logout")
  public CommonResponse<String> logout(@AuthenticationPrincipal AuthId authId) {
    log.info(SecurityContextHolder.getContext().getAuthentication().getName());
    storeManagerService.logout(authId);
    return CommonResponse.success("스토어 매니저 로그아웃 완료");
  }


}
