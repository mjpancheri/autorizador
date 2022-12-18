package com.mjpancheri.authorizer.cards.application.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mjpancheri.authorizer.cards.domain.Card;
import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCardDto {
  private static final double INITIAL_BALANCE = 500.0;

  @NotEmpty(message = "Número do cartão obrigatório")
  @JsonProperty(value = "numeroCartao")
  private String number;

  @NotEmpty(message = "Senha obrigatória")
  @JsonProperty(value = "senha")
  private String password;


  public Card toCard() {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    return Card.builder()
        .number(number)
        .password(bCryptPasswordEncoder.encode(password))
        .balance(BigDecimal.valueOf(INITIAL_BALANCE))
        .build();
  }
}
