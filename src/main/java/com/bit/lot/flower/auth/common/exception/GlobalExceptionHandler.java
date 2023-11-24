package com.bit.lot.flower.auth.common.exception;

import com.bit.lot.flower.auth.email.exception.EmailCodeException;
import com.bit.lot.flower.auth.social.exception.SocialAuthException;
import com.bit.lot.flower.auth.store.exception.StoreManagerAuthException;
import io.jsonwebtoken.JwtException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, List<String>>> handleValidationErrors(
      MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult().getFieldErrors()
        .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  private Map<String, List<String>> getErrorsMap(List<String> errors) {
    Map<String, List<String>> errorResponse = new HashMap<>();
    errorResponse.put("errors", errors);
    return errorResponse;
  }
  @ExceptionHandler(JwtException.class)
  public ResponseEntity<Map<String, List<String>>> jwtException(
      JwtException ex) {
    List<String> errors = Collections.singletonList(ex.getMessage());
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
  }


  @ExceptionHandler(EmailCodeException.class)
  public ResponseEntity<Map<String, List<String>>> emailCodeException(
      EmailCodeException ex) {
    List<String> errors = Collections.singletonList(ex.getMessage());
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
  }
  @ExceptionHandler(StoreManagerAuthException.class)
  public ResponseEntity<Map<String, List<String>>> storeManagerException(
      StoreManagerAuthException ex) {
    List<String> errors = Collections.singletonList(ex.getMessage());
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
  }


  @ExceptionHandler(SocialAuthException.class)
  public ResponseEntity<Map<String, List<String>>> socialAuthException(
      EmailCodeException ex) {
    List<String> errors = Collections.singletonList(ex.getMessage());
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<Map<String, List<String>>> handleGeneralExceptions(Exception ex) {
    List<String> errors = Collections.singletonList(ex.getMessage());
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RuntimeException.class)
  public final ResponseEntity<Map<String, List<String>>> handleRuntimeExceptions(
      RuntimeException ex) {
    List<String> errors = Collections.singletonList(ex.getMessage());
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }


}