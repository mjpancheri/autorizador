package com.mjpancheri.authorizer.cards.infrastructure.mapper;

import com.mjpancheri.authorizer.cards.application.rest.dto.CreateCardDto;
import com.mjpancheri.authorizer.cards.domain.Card;
import java.math.BigDecimal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CardMapper {
  private static final double INITIAL_BALANCE = 500.0;

  private CardMapper() {}

  public static Card createCardDtoToCard(CreateCardDto createCardDto) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    if (createCardDto == null) {
      return null;
    }
    return Card.builder()
        .number(createCardDto.getNumeroCartao())
        .password(bCryptPasswordEncoder.encode(createCardDto.getSenha()))
        .balance(BigDecimal.valueOf(INITIAL_BALANCE))
        .build();
  }

  public static CreateCardDto cardToCreateCardDto(Card card) {
    if (card == null) {
      return null;
    }

    return CreateCardDto.builder()
        .numeroCartao(card.getNumber())
        .senha(card.getPassword())
        .build();
  }
}
