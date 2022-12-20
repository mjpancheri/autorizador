package com.mjpancheri.authorizer.cards.infrastructure.mapper;

import com.mjpancheri.authorizer.cards.domain.Card;
import com.mjpancheri.authorizer.cards.domain.Transaction;
import java.math.BigDecimal;

public class TransactionMapper {

  private TransactionMapper(){}

  public static Transaction convertToTransaction(BigDecimal amount, Card card) {
    if (amount == null || card == null) {
      return null;
    }

    return Transaction.builder()
        .amount(amount)
        .card(card)
        .build();
  }

}
