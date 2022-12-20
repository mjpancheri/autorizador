package com.mjpancheri.authorizer.cards.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.mjpancheri.authorizer.cards.application.rest.builder.CardBuilder;
import com.mjpancheri.authorizer.cards.domain.repository.CardRepository;
import com.mjpancheri.authorizer.common.exception.CardIncorrectException;
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

  @Test
  @DisplayName("Retorna o cartão quando encontra")
  void getCardOk() {
    var card = CardBuilder.getCard();

    when(cardRepository.findByNumber(anyString())).thenReturn(Optional.of(card));
    var foundCard = cardService.getCard(card.getNumber());

    assertThat(foundCard.getId()).isEqualTo(card.getId());
  }

  @Test
  @DisplayName("Retorna erro quando o cartão não existe")
  void getCardNotFound() {
    var wrongNumber = "1234123412341234";

    when(cardRepository.findByNumber(anyString())).thenThrow(CardIncorrectException.class);

    assertThatThrownBy(
        () -> cardService.getCard(wrongNumber)
    ).isInstanceOf(CardIncorrectException.class);
  }

  @Test
  @DisplayName("Retorna true se a senha estiver correta")
  void validCardPasswordOk() {
    var card = CardBuilder.getCard();
    var rightPassword = "1234";

    var valid = cardService.validCardPassword(card, rightPassword);

    assertThat(valid).isTrue();
  }

  @Test
  @DisplayName("Retorna false se a senha extiver incorreta")
  void validCardPasswordWithIncorrectPassword() {
    var card = CardBuilder.getCard();
    var wrongPassword = "0000";
    var valid = cardService.validCardPassword(card, wrongPassword);

    assertThat(valid).isFalse();
  }

  @Test
  @DisplayName("Retorna false se o cartão for null")
  void validCardPasswordWithInvalidCard() {

    var valid = cardService.validCardPassword(null, "1234");

    assertThat(valid).isFalse();
  }

  @Test
  @DisplayName("Retorna false se a senha for null")
  void validCardPasswordWithInvalidPassword() {
    var card = CardBuilder.getCard();

    var valid = cardService.validCardPassword(card, null);

    assertThat(valid).isFalse();
  }
}
