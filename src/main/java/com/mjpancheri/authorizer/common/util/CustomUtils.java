package com.mjpancheri.authorizer.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjpancheri.authorizer.common.exception.ConvertToJsonException;

public class CustomUtils {

  public static final String TRANSACTION_OK = "OK";
  public static final String TRANSACTION_INSUFFICIENT_BALANCE = "SALDO_INSUFICIENTE";
  public static final String TRANSACTION_INCORRECT_PASSWORD = "SENHA_INVALIDA";
  public static final String TRANSACTION_INCORRECT_CARD = "CARTAO_INEXISTENTE";
  public static final String CORRECT_CARD_NUMBER_MASK = "################";
  public static final String INCORRECT_CARD_NUMBER_MASK = "########";
  public static final String INVALID_CARD_NUMBER_MASK = "????????????????";
  public static final String CORRECT_PASSWORD_MASK = "####";
  public static final String INCORRECT_PASSWORD_MASK = "##";
  public static final String INVALID_PASSWORD_MASK = "????";

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
