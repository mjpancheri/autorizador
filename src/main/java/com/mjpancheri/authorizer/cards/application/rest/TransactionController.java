package com.mjpancheri.authorizer.cards.application.rest;

import com.mjpancheri.authorizer.cards.application.rest.dto.CreateTransactionDto;
import com.mjpancheri.authorizer.cards.application.rest.swagger.api.TransactionApi;
import com.mjpancheri.authorizer.cards.application.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransactionController implements TransactionApi {

  private final TransactionService transactionService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Override
  public String create(@RequestBody @Validated CreateTransactionDto createTransactionDto) {
    return transactionService.createTransaction(createTransactionDto);
  }
}
