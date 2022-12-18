package com.mjpancheri.authorizer.cards.application.rest.dto;

import com.mjpancheri.authorizer.cards.domain.Card;
import com.mjpancheri.authorizer.common.validation.NumbersOnly;
import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
  @Size(min = 16, max = 16, message = "Deve conter 16 digitos")
  @NumbersOnly
  private String numeroCartao;

  @NotEmpty(message = "Senha obrigatória")
  @Size(min = 4, max = 4, message = "Deve conter 4 digitos")
  @NumbersOnly
  private String senha;


  public Card toCard() {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    return Card.builder()
        .number(numeroCartao)
        .password(bCryptPasswordEncoder.encode(senha))
        .balance(BigDecimal.valueOf(INITIAL_BALANCE))
        .build();
  }
}
