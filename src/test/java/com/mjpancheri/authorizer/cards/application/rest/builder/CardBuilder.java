package com.mjpancheri.authorizer.cards.application.rest.builder;

import static com.mjpancheri.authorizer.common.util.CustomUtils.CORRECT_CARD_NUMBER_MASK;
import static com.mjpancheri.authorizer.common.util.CustomUtils.CORRECT_PASSWORD_MASK;
import static com.mjpancheri.authorizer.common.util.CustomUtils.INCORRECT_CARD_NUMBER_MASK;
import static com.mjpancheri.authorizer.common.util.CustomUtils.INCORRECT_PASSWORD_MASK;
import static com.mjpancheri.authorizer.common.util.CustomUtils.INVALID_CARD_NUMBER_MASK;
import static com.mjpancheri.authorizer.common.util.CustomUtils.INVALID_PASSWORD_MASK;

import com.github.javafaker.Faker;
import com.mjpancheri.authorizer.cards.application.rest.dto.CreateCardDto;
import com.mjpancheri.authorizer.cards.domain.Card;
import java.math.BigDecimal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CardBuilder {

  private static final Faker faker = new Faker();
  private static final BigDecimal BALANCE_FULL = BigDecimal.valueOf(500.0);
  private static final BigDecimal BALANCE_EMPTY = BigDecimal.ZERO;
  private CardBuilder(){}

  public static CreateCardDto getCreateCardDto() {
    return CreateCardDto.builder()
        .numeroCartao(faker.numerify(CORRECT_CARD_NUMBER_MASK))
        .senha(faker.numerify(CORRECT_PASSWORD_MASK))
        .build();
  }

  public static CreateCardDto getCreateCardDtoNumberIncorrect() {
    return CreateCardDto.builder()
        .numeroCartao(faker.numerify(INCORRECT_CARD_NUMBER_MASK))
        .senha(faker.numerify(CORRECT_PASSWORD_MASK))
        .build();
  }

  public static CreateCardDto getCreateCardDtoNumberInvalid() {
    return CreateCardDto.builder()
        .numeroCartao(faker.letterify(INVALID_CARD_NUMBER_MASK))
        .senha(faker.numerify(CORRECT_PASSWORD_MASK))
        .build();
  }

  public static CreateCardDto getCreateCardDtoPasswordIncorrect() {
    return CreateCardDto.builder()
        .numeroCartao(faker.numerify(CORRECT_CARD_NUMBER_MASK))
        .senha(faker.numerify(INCORRECT_PASSWORD_MASK))
        .build();
  }

  public static CreateCardDto getCreateCardDtoPasswordInvalid() {
    return CreateCardDto.builder()
        .numeroCartao(faker.numerify(CORRECT_CARD_NUMBER_MASK))
        .senha(faker.letterify(INVALID_PASSWORD_MASK))
        .build();
  }

  public static Card getCard() {
    BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
    return Card.builder()
        .id(1L)
        .number(faker.numerify(CORRECT_CARD_NUMBER_MASK))
        .password(bCrypt.encode("1234"))
        .balance(BALANCE_FULL)
        .build();
  }

  public static Card getCardBalanceZero() {
    BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
    return Card.builder()
        .id(1L)
        .number(faker.numerify(CORRECT_CARD_NUMBER_MASK))
        .password(bCrypt.encode("1234"))
        .balance(BALANCE_EMPTY)
        .build();
  }
}
