package com.mjpancheri.authorizer.cards.application.rest.swagger.api;

import com.mjpancheri.authorizer.cards.application.rest.dto.CreateCardDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Card Api")
public interface CardApi {

  @Operation(summary = "Cria um novo cartão com saldo inicial de R$ 500,00.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Cartão criado com sucesso."),
      @ApiResponse(responseCode = "422", description = "Cartão já existe.")
  })
  CreateCardDto create(@RequestBody @Validated CreateCardDto createCardRequest);

  @Operation(summary = "Verifica o saldo do cartão")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Cartão encotrado."),
      @ApiResponse(responseCode = "404", description = "Cartão inexiste.")
  })
  Double balance(@PathVariable String cardNumber);

}
