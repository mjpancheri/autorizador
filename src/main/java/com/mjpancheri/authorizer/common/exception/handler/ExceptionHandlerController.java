package com.mjpancheri.authorizer.common.exception.handler;

import com.mjpancheri.authorizer.cards.application.rest.dto.CreateCardDto;
import com.mjpancheri.authorizer.common.exception.CardIncorrectException;
import com.mjpancheri.authorizer.common.exception.CardNotFoundException;
import com.mjpancheri.authorizer.common.exception.CardNumberConflictException;
import com.mjpancheri.authorizer.common.exception.InsufficientBalanceException;
import com.mjpancheri.authorizer.common.exception.PasswordIncorrectException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class ExceptionHandlerController {

  @ExceptionHandler(CardNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void handleCardNotFoundException() {

  }

  @ExceptionHandler(CardNumberConflictException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public CreateCardDto handleCardNumberConflictException(CreateCardDto createCardDto) {
    return createCardDto;
  }

  @ExceptionHandler(CardIncorrectException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public String handleCardIncorrectException() {
    return "CARTAO_INEXISTENTE";
  }

  @ExceptionHandler(PasswordIncorrectException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public String handlePasswordIncorrectException() {
    return "SENHA_INVALIDA";
  }

  @ExceptionHandler(InsufficientBalanceException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public String handleInsufficientBalanceException() {
    return "SALDO_INSUFICIENTE";
  }
}
