package com.mjpancheri.authorizer.common.exception;

import com.mjpancheri.authorizer.cards.application.rest.dto.CreateCardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardNumberConflictException extends RuntimeException {
  private final CreateCardDto payload;
}
