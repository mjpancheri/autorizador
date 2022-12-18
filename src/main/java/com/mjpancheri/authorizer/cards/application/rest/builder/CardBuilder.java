package com.mjpancheri.authorizer.cards.application.rest.builder;

import com.github.javafaker.Faker;
import com.mjpancheri.authorizer.cards.application.rest.dto.CreateCardDto;
import com.mjpancheri.authorizer.cards.domain.Card;
import java.math.BigDecimal;

public class CardBuilder {

  private static final Faker faker = new Faker();
  private static final String CORRECT_CARD_NUMBER_MASK = "################";
  private static final String INCORRECT_CARD_NUMBER_MASK = "########";
  private static final String INVALID_CARD_NUMBER_MASK = "????????????????";
  private static final String CORRECT_PASSWORD_MASK = "####";
  private static final String INCORRECT_PASSWORD_MASK = "##";
  private static final String INVALID_PASSWORD_MASK = "????";
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
    return Card.builder()
        .id(1L)
        .number(faker.numerify(CORRECT_CARD_NUMBER_MASK))
        .password(faker.numerify(CORRECT_PASSWORD_MASK))
        .balance(BigDecimal.valueOf(500.0))
        .build();
  }
}
