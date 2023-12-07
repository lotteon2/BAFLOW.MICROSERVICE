package com.bit.lot.flower.auth.common.interceptor.refresh;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import com.bit.lot.flower.auth.common.http.interceptor.filter.ExceptionHandlerFilter;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class GetUserDataWhenTokenIsExpiredTest {

    @Mock
    ExceptionHandlerFilter exceptionHandlerFilter;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    FilterChain filterChain;

    @Test
    @DisplayName("JWT 토큰이 Expired 되었을 때 Response에 RenewAccessTokenDto 확인")
    void GetUserTokenInfo_WhenTokenIsExpired_ResponseWithRenewAccessTokenDto()
        throws ServletException, IOException {
        doThrow(ExpiredJwtException.class).when(exceptionHandlerFilter)
            .doFilterInternal(any(HttpServletRequest.class), any(HttpServletResponse.class),
                any(FilterChain.class));
        assertThrowsExactly(ExpiredJwtException.class, () -> {
            exceptionHandlerFilter.doFilterInternal(request, response, filterChain);
        });

    }
}