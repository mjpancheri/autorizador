package com.mjpancheri.authorizer.cards.application.rest.builder;

import static com.mjpancheri.authorizer.common.util.CustomUtils.CORRECT_CARD_NUMBER_MASK;
import static com.mjpancheri.authorizer.common.util.CustomUtils.CORRECT_PASSWORD_MASK;
import static com.mjpancheri.authorizer.common.util.CustomUtils.INCORRECT_CARD_NUMBER_MASK;
import static com.mjpancheri.authorizer.common.util.CustomUtils.INCORRECT_PASSWORD_MASK;
import static com.mjpancheri.authorizer.common.util.CustomUtils.INVALID_CARD_NUMBER_MASK;
import static com.mjpancheri.authorizer.common.util.CustomUtils.INVALID_PASSWORD_MASK;

import com.github.javafaker.Faker;
import com.mjpancheri.authorizer.cards.application.rest.dto.CreateTransactionDto;
import com.mjpancheri.authorizer.cards.domain.Transaction;
import java.math.BigDecimal;

public class TransactionBuilder {

  private static final Faker faker = new Faker();
  private static final BigDecimal AMOUNT = BigDecimal.valueOf(100.0);

  private TransactionBuilder(){}

  public static CreateTransactionDto getCreateTransactionDto() {
    return CreateTransactionDto.builder()
        .numeroCartao(faker.numerify(CORRECT_CARD_NUMBER_MASK))
        .senhaCartao(faker.numerify(CORRECT_PASSWORD_MASK))
        .valor(AMOUNT)
        .build();
  }

  public static CreateTransactionDto getCreateTransactionDtoNumberIncorrect() {
    return CreateTransactionDto.builder()
        .numeroCartao(faker.numerify(INCORRECT_CARD_NUMBER_MASK))
        .senhaCartao(faker.numerify(CORRECT_PASSWORD_MASK))
        .valor(AMOUNT)
        .build();
  }

  public static CreateTransactionDto getCreateTransactionDtoNumberInvalid() {
    return CreateTransactionDto.builder()
        .numeroCartao(faker.letterify(INVALID_CARD_NUMBER_MASK))
        .senhaCartao(faker.numerify(CORRECT_PASSWORD_MASK))
        .valor(AMOUNT)
        .build();
  }

  public static CreateTransactionDto getCreateTransactionDtoPasswordIncorrect() {
    return CreateTransactionDto.builder()
        .numeroCartao(faker.numerify(CORRECT_CARD_NUMBER_MASK))
        .senhaCartao(faker.numerify(INCORRECT_PASSWORD_MASK))
        .valor(AMOUNT)
        .build();
  }

  public static CreateTransactionDto getCreateTransactionDtoPasswordInvalid() {
    return CreateTransactionDto.builder()
        .numeroCartao(faker.numerify(CORRECT_CARD_NUMBER_MASK))
        .senhaCartao(faker.letterify(INVALID_PASSWORD_MASK))
        .valor(AMOUNT)
        .build();
  }

  public static CreateTransactionDto getCreateTransactionDtoWithoutAmount() {
    return CreateTransactionDto.builder()
        .numeroCartao(faker.numerify(CORRECT_CARD_NUMBER_MASK))
        .senhaCartao(faker.numerify(CORRECT_PASSWORD_MASK))
        .build();
  }

  public static Transaction getTransaction() {
    var card = CardBuilder.getCard();
    return Transaction.builder()
        .card(card)
        .amount(AMOUNT)
        .build();
  }
}
