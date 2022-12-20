package com.mjpancheri.authorizer.cards.application.rest.swagger.api;

import com.mjpancheri.authorizer.cards.application.rest.dto.CreateTransactionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Transaction Api")
public interface TransactionApi {

  @Operation(
      summary = "Cria uma nova transação subtraindo o valor do saldo do cartão",
      requestBody = @RequestBody(
          required = true,
          content = {
              @Content(
                  mediaType = "application/json",
                  examples = {
                      @ExampleObject(
                          value = """
                              {
                                "numeroCartao": "1234123412341234",
                                "senhaCartao": "1234",
                                "valor": 10.59
                              }
                              """
                      )
                  }
              )
          }
      ),
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "Transação criada com sucesso.",
              content = @Content(
                  mediaType = "plan/text",
                  examples = {
                      @ExampleObject(
                          value = "OK"
                      )
                  }
              )
          ),
          @ApiResponse(
              responseCode = "400",
              description = "Dados inválidos.",
              content = @Content(
                  mediaType = "plan/text",
                  examples = {
                      @ExampleObject(
                          value = "numeroCartao: Somente números"
                      )
                  }
              )
          ),
          @ApiResponse(
              responseCode = "404",
              description = "Não implementado"
          ),
          @ApiResponse(
              responseCode = "422",
              description = "Problemas com o cartão [SALDO_INSUFICIENTE|SENHA_INVALIDA|CARTAO_INEXISTENTE].",
              content = @Content(
                  mediaType = "plan/text",
                  examples = {
                      @ExampleObject(
                          value = "SALDO_INSUFICIENTE"
                      )
                  }
              )
          ),
          @ApiResponse(
              responseCode = "500",
              description = "Não implementado"
          )
      }
  )
  String create(CreateTransactionDto createTransactionDto);
}
