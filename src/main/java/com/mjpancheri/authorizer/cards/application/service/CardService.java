package com.mjpancheri.authorizer.cards.application.service;

import com.mjpancheri.authorizer.cards.application.rest.dto.CreateCardDto;
import com.mjpancheri.authorizer.cards.domain.Card;
import com.mjpancheri.authorizer.cards.domain.repository.CardRepository;
import com.mjpancheri.authorizer.cards.infrastructure.mapper.CardMapper;
import com.mjpancheri.authorizer.common.exception.CardNotFoundException;
import com.mjpancheri.authorizer.common.exception.CardNumberConflictException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CardService {

  private final CardRepository cardRepository;

  public CreateCardDto createCard(CreateCardDto createCardDto) {
    if (cardRepository.existsByNumber(createCardDto.getNumeroCartao())) {
      throw new CardNumberConflictException(createCardDto);
    }
    cardRepository.save(CardMapper.createCardDtoToCard(createCardDto));
    return createCardDto;
  }

  public Double getBalance(String cardNumber) {
    Card card = cardRepository.findByNumber(cardNumber)
        .orElseThrow(CardNotFoundException::new);

    return card.getBalance().doubleValue();
  }
}
