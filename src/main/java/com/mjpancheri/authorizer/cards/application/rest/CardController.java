package com.mjpancheri.authorizer.cards.application.rest;

import com.mjpancheri.authorizer.cards.application.rest.dto.CreateCardDto;
import com.mjpancheri.authorizer.cards.application.rest.swagger.api.CardApi;
import com.mjpancheri.authorizer.cards.application.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CardController implements CardApi {

  private final CardService cardService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Override
  public CreateCardDto create(CreateCardDto createCardRequest) {
    return cardService.createCard(createCardRequest);
  }

  @GetMapping("/{cardNumber}")
  @ResponseStatus(HttpStatus.OK)
  @Override
  public Double balance(String cardNumber) {
    return cardService.getBalance(cardNumber);
  }
}
