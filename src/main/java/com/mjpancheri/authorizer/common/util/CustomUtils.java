package com.mjpancheri.authorizer.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjpancheri.authorizer.common.exception.ConvertToJsonException;

public class CustomUtils {

  private CustomUtils(){}

  public static String asJsonString(final Object obj) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.findAndRegisterModules();
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new ConvertToJsonException();
    }
  }
}
