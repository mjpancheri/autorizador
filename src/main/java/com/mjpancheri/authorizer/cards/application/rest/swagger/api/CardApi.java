package com.mjpancheri.authorizer.cards.application.rest.swagger.api;

import com.mjpancheri.authorizer.cards.application.rest.dto.CreateCardDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Card Api")
public interface CardApi {

  @Operation(
      summary = "Cria um novo cartão com saldo inicial de R$ 500,00.",
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
                                "senha": "1234"
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
              description = "Cartão criado com sucesso.",
              content = @Content(
                  mediaType = "application/json",
                  examples = {
                      @ExampleObject(
                          value = """
                              {
                                "numeroCartao": "1234123412341234",
                                "senha": "1234"
                              }
                              """
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
              description = "Cartão já existe.",
              content = @Content(
                  mediaType = "application/json",
                  examples = {
                      @ExampleObject(
                          value = """
                              {
                                "numeroCartao": "1234123412341234",
                                "senha": "1234"
                              }
                              """
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
  CreateCardDto create(CreateCardDto createCardRequest);

  @Operation(
      summary = "Verifica o saldo do cartão",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Cartão encontrado.",
              content = @Content(
                  examples = {
                      @ExampleObject(
                          value = "490.45"
                      )
                  }
              )
          ),
          @ApiResponse(
              responseCode = "400",
              description = "Não implementado"
          ),
          @ApiResponse(
              responseCode = "404",
              description = "Cartão não existe"
          ),
          @ApiResponse(
              responseCode = "422",
              description = "Não implementado"
          ),
          @ApiResponse(
              responseCode = "500",
              description = "Não implementado"
          )
      }
  )
  Double balance(String cardNumber);

}
