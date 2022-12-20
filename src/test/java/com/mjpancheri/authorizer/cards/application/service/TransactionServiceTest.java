package com.mjpancheri.authorizer.cards.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.mjpancheri.authorizer.cards.application.rest.builder.CardBuilder;
import com.mjpancheri.authorizer.cards.application.rest.builder.TransactionBuilder;
import com.mjpancheri.authorizer.cards.domain.Card;
import com.mjpancheri.authorizer.cards.domain.repository.TransactionRepository;
import com.mjpancheri.authorizer.common.exception.CardIncorrectException;
import com.mjpancheri.authorizer.common.exception.InsufficientBalanceException;
import com.mjpancheri.authorizer.common.exception.PasswordIncorrectException;
import com.mjpancheri.authorizer.common.util.CustomUtils;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TransactionServiceTest {

  @Mock
  private TransactionRepository transactionRepository;
  @Mock
  private CardService cardService;
  @InjectMocks
  private TransactionService transactionService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Cria transação corretamente quando os dados são válidos")
  void createTransactionOk() {
    var transactionPayload = TransactionBuilder.getCreateTransactionDto();
    var card = CardBuilder.getCard();

    when(cardService.getCard(anyString())).thenReturn(card);
    when(cardService.validCardPassword(any(Card.class), anyString())).thenReturn(Boolean.TRUE);
    doNothing().when(cardService).updateCardBalance(any(Card.class), any(BigDecimal.class));
    var result = transactionService.createTransaction(transactionPayload);

    assertThat(result).isEqualTo(CustomUtils.TRANSACTION_OK);
  }

  @Test
  @DisplayName("Retorna erro de transação quando não encontra o cartão")
  void createTransactionIncorrectCard() {
    var transactionPayload = TransactionBuilder.getCreateTransactionDto();

    when(cardService.getCard(anyString())).thenThrow(CardIncorrectException.class);

    assertThatThrownBy(
        () -> transactionService.createTransaction(transactionPayload)
    ).isInstanceOf(CardIncorrectException.class);
  }

  @Test
  @DisplayName("Retorna erro de transação quando a senha do cartão está incorreta")
  void createTransactionIncorrectPassword() {
    var transactionPayload = TransactionBuilder.getCreateTransactionDto();
    var card = CardBuilder.getCard();

    when(cardService.getCard(anyString())).thenReturn(card);
    when(cardService.validCardPassword(any(Card.class), anyString())).thenReturn(Boolean.FALSE);

    assertThatThrownBy(
        () -> transactionService.createTransaction(transactionPayload)
    ).isInstanceOf(PasswordIncorrectException.class);
  }

  @Test
  @DisplayName("Retorna erro de transação quando o saldo do cartão é insuficiente")
  void createTransactionInsufficientBalance() {
    var transactionPayload = TransactionBuilder.getCreateTransactionDto();
    var card = CardBuilder.getCardBalanceZero();

    when(cardService.getCard(anyString())).thenReturn(card);
    when(cardService.validCardPassword(any(Card.class), anyString())).thenReturn(Boolean.TRUE);

    assertThatThrownBy(
        () -> transactionService.createTransaction(transactionPayload)
    ).isInstanceOf(InsufficientBalanceException.class);
  }
}
