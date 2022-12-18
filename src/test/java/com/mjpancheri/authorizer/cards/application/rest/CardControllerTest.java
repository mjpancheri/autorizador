package com.mjpancheri.authorizer.cards.application.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mjpancheri.authorizer.cards.application.rest.builder.CardBuilder;
import com.mjpancheri.authorizer.cards.application.rest.dto.CreateCardDto;
import com.mjpancheri.authorizer.cards.application.service.CardService;
import com.mjpancheri.authorizer.common.exception.CardNotFoundException;
import com.mjpancheri.authorizer.common.exception.CardNumberConflictException;
import com.mjpancheri.authorizer.common.exception.handler.ExceptionHandlerController;
import com.mjpancheri.authorizer.common.util.CustomUtils;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class CardControllerTest {

  private static final String ENDPOINT_URL = "/cartoes";

  @Mock
  private CardService cardService;
  @InjectMocks
  private CardController cardController;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(cardController)
        .setControllerAdvice(new ExceptionHandlerController()).build();
  }

  @Test
  @DisplayName("Cria um novo cartão quando o payload está correto")
  void createOk() throws Exception {
    var payload = CardBuilder.getCreateCardDto();
    when(cardService.createCard(any(CreateCardDto.class))).thenReturn(payload);

    mockMvc.perform(
        post(ENDPOINT_URL).contentType(MediaType.APPLICATION_JSON)
            .content(CustomUtils.asJsonString(payload))
        ).andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.numeroCartao", Is.is(payload.getNumeroCartao())))
        .andExpect(jsonPath("$.senha", Is.is(payload.getSenha())));
    verify(cardService, times(1))
        .createCard(any(CreateCardDto.class));
    verifyNoMoreInteractions(cardService);
  }

  @Test
  @DisplayName("Retorna erro quando cartão já existe")
  void createConflict() throws Exception {
    var payload = CardBuilder.getCreateCardDto();
    when(cardService.createCard(any(CreateCardDto.class))).thenThrow(CardNumberConflictException.class);

    mockMvc.perform(
            post(ENDPOINT_URL).contentType(MediaType.APPLICATION_JSON)
                .content(CustomUtils.asJsonString(payload))
        ).andExpect(status().isUnprocessableEntity());
    verify(cardService, times(1))
        .createCard(any(CreateCardDto.class));
    verifyNoMoreInteractions(cardService);
  }

  @Test
  @DisplayName("Retorna erro de validação quando número do cartão está com tamanho incorreto")
  void createErrorCardNumberIncorrect() throws Exception {
    var payload = CardBuilder.getCreateCardDtoNumberIncorrect();

    mockMvc.perform(
        post(ENDPOINT_URL).contentType(MediaType.APPLICATION_JSON)
            .content(CustomUtils.asJsonString(payload))
        ).andExpect(status().isBadRequest())
        .andExpect(content().string("numeroCartao: Deve conter 16 digitos; "));
    verify(cardService, times(0))
        .createCard(any(CreateCardDto.class));
    verifyNoMoreInteractions(cardService);
  }

  @Test
  @DisplayName("Retorna erro de validação quando número do cartão é inválido")
  void createErrorCardNumberInvalid() throws Exception {
    var payload = CardBuilder.getCreateCardDtoNumberInvalid();

    mockMvc.perform(
            post(ENDPOINT_URL).contentType(MediaType.APPLICATION_JSON)
                .content(CustomUtils.asJsonString(payload))
        ).andExpect(status().isBadRequest())
        .andExpect(content().string("numeroCartao: Somente números; "));
    verify(cardService, times(0))
        .createCard(any(CreateCardDto.class));
    verifyNoMoreInteractions(cardService);
  }

  @Test
  @DisplayName("Retorna erro de validação quando senha está com tamanho incorreto")
  void createErrorPasswordIncorrect() throws Exception {
    var payload = CardBuilder.getCreateCardDtoPasswordIncorrect();

    mockMvc.perform(
            post(ENDPOINT_URL).contentType(MediaType.APPLICATION_JSON)
                .content(CustomUtils.asJsonString(payload))
        ).andExpect(status().isBadRequest())
        .andExpect(content().string("senha: Deve conter 4 digitos; "));
    verify(cardService, times(0))
        .createCard(any(CreateCardDto.class));
    verifyNoMoreInteractions(cardService);
  }

  @Test
  @DisplayName("Retorna erro de validação quando senha é inválida")
  void createErrorPasswordInvalid() throws Exception {
    var payload = CardBuilder.getCreateCardDtoPasswordInvalid();

    mockMvc.perform(
            post(ENDPOINT_URL).contentType(MediaType.APPLICATION_JSON)
                .content(CustomUtils.asJsonString(payload))
        ).andExpect(status().isBadRequest())
        .andExpect(content().string("senha: Somente números; "));
    verify(cardService, times(0))
        .createCard(any(CreateCardDto.class));
    verifyNoMoreInteractions(cardService);
  }

  @Test
  @DisplayName("Retorna saldo atual quando o cartão existe")
  void balanceOk() throws Exception {
    var card = CardBuilder.getCard();
    when(cardService.getBalance(anyString())).thenReturn(card.getBalance().doubleValue());

    mockMvc.perform(
            get(ENDPOINT_URL + "/" + card.getNumber()).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        .andExpect(content().string(String.valueOf(card.getBalance().doubleValue())));
    verify(cardService, times(1))
        .getBalance(anyString());
    verifyNoMoreInteractions(cardService);
  }

  @Test
  @DisplayName("Retorna erro quando o cartão não for encontrado")
  void balanceCardNotFound() throws Exception {
    var card = CardBuilder.getCard();
    when(cardService.getBalance(anyString())).thenThrow(CardNotFoundException.class);

    mockMvc.perform(
            get(ENDPOINT_URL + "/" + card.getNumber()).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    verify(cardService, times(1))
        .getBalance(anyString());
    verifyNoMoreInteractions(cardService);
  }
}
