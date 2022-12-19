package com.mjpancheri.authorizer.cards.application.rest.dto;

import com.mjpancheri.authorizer.common.validation.NumbersOnly;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCardDto {

  @NotEmpty(message = "Número do cartão obrigatório")
  @Size(min = 16, max = 16, message = "Deve conter 16 digitos")
  @NumbersOnly
  private String numeroCartao;

  @NotEmpty(message = "Senha obrigatória")
  @Size(min = 4, max = 4, message = "Deve conter 4 digitos")
  @NumbersOnly
  private String senha;

}
