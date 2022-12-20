package com.mjpancheri.authorizer.common.exception.handler;

import com.mjpancheri.authorizer.cards.application.rest.dto.CreateCardDto;
import com.mjpancheri.authorizer.common.exception.CardIncorrectException;
import com.mjpancheri.authorizer.common.exception.CardNotFoundException;
import com.mjpancheri.authorizer.common.exception.CardNumberConflictException;
import com.mjpancheri.authorizer.common.exception.ConvertToJsonException;
import com.mjpancheri.authorizer.common.exception.InsufficientBalanceException;
import com.mjpancheri.authorizer.common.exception.PasswordIncorrectException;
import com.mjpancheri.authorizer.common.util.CustomUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class ExceptionHandlerController {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {

    return exception.getFieldErrors()
        .stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage() + "; ")
        .reduce("", String::concat);
  }

  @ExceptionHandler(CardNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleCardNotFoundException() {
    return "";
  }

  @ExceptionHandler(CardNumberConflictException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public CreateCardDto handleCardNumberConflictException(CardNumberConflictException exception) {
    return exception.getPayload();
  }

  @ExceptionHandler(ConvertToJsonException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleConvertToJsonException(Exception exception) {
    return exception.getMessage();
  }

  @ExceptionHandler(CardIncorrectException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public String handleCardIncorrectException() {
    return CustomUtils.TRANSACTION_INCORRECT_CARD;
  }

  @ExceptionHandler(PasswordIncorrectException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public String handlePasswordIncorrectException() {
    return CustomUtils.TRANSACTION_INCORRECT_PASSWORD;
  }

  @ExceptionHandler(InsufficientBalanceException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public String handleInsufficientBalanceException() {
    return CustomUtils.TRANSACTION_INSUFFICIENT_BALANCE;
  }
}
