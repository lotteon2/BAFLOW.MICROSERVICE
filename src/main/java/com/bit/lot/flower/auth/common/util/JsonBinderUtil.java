package com.bit.lot.flower.auth.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

public class JsonBinderUtil {

  public static HttpServletResponse setResponseWithJson(HttpServletResponse response, int status,
      Object type) throws IOException {

    response.setContentType("application/json");
    response.setStatus(status);
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(getJsonFromType(type));
    return response;
  }

  private static String getJsonFromType(Object type) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(type);
  }

}
