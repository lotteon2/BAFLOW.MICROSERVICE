package com.bit.lot.flower.auth.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

public class JsonBinderUtil {

  public static HttpServletResponse setResponseWithJson(HttpServletResponse response, int status,
      Object type) throws IOException {

    response.setContentType("application/json");
    response.setStatus(status);
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(getJsonFromType(type));
    return response;
  }


  public static HttpServletResponse setRedirectURLWithPathVariableType(HttpServletResponse response, int status,
      Object type) throws IOException {
    response.setContentType("application/json");
    response.setStatus(HttpStatus.PERMANENT_REDIRECT.value());
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(getJsonFromType(type));
    response.sendRedirect("");
    return response;
  }


  private static String getJsonFromType(Object type) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(type);
  }

}
