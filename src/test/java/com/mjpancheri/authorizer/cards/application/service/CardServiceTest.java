package com.mjpancheri.authorizer.cards.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.mjpancheri.authorizer.cards.application.rest.builder.CardBuilder;
import com.mjpancheri.authorizer.cards.domain.repository.CardRepository;
import com.mjpancheri.authorizer.common.exception.CardNotFoundException;
import com.mjpancheri.authorizer.common.exception.CardNumberConflictException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CardServiceTest {

  @Mock
  private CardRepository cardRepository;
  @InjectMocks
  private CardService cardService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Cria cartão corretamente quando os dados são válidos")
  void createCardOk() {
    var cardPayload = CardBuilder.getCreateCardDto();

    when(cardRepository.existsByNumber(anyString())).thenReturn(Boolean.FALSE);
    var newCard = cardService.createCard(cardPayload);

    assertThat(newCard.getNumeroCartao()).isEqualTo(cardPayload.getNumeroCartao());
  }

  @Test
  @DisplayName("Retorna erro de cartão existente se número de cartão já foi cadastrado")
  void createCardConflict() {
    var cardPayload = CardBuilder.getCreateCardDto();

    when(cardRepository.existsByNumber(anyString())).thenReturn(Boolean.TRUE);

    assertThatThrownBy(
        () -> cardService.createCard(cardPayload)
    ).isInstanceOf(CardNumberConflictException.class);
  }

  @Test
  @DisplayName("Retorna saldo atual quando encontra o cartão")
  void getBalanceOk() {
    var card = CardBuilder.getCard();

    when(cardRepository.findByNumber(anyString())).thenReturn(Optional.of(card));
    var balance = cardService.getBalance(card.getNumber());

    assertThat(balance).isEqualTo(card.getBalance().doubleValue());
  }

  @Test
  @DisplayName("Retorna erro quando o cartão não existir")
  void getBalanceIncorrectCard() {
    var card = CardBuilder.getCard();
    var cardNumber = card.getNumber();

    when(cardRepository.findByNumber(anyString())).thenThrow(CardNotFoundException.class);

    assertThatThrownBy(
        () -> cardService.getBalance(cardNumber)
    ).isInstanceOf(CardNotFoundException.class);
  }
}
