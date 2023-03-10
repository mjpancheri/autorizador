package com.mjpancheri.authorizer.cards.application.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mjpancheri.authorizer.cards.application.rest.builder.TransactionBuilder;
import com.mjpancheri.authorizer.cards.application.rest.dto.CreateTransactionDto;
import com.mjpancheri.authorizer.cards.application.service.TransactionService;
import com.mjpancheri.authorizer.common.exception.CardIncorrectException;
import com.mjpancheri.authorizer.common.exception.InsufficientBalanceException;
import com.mjpancheri.authorizer.common.exception.PasswordIncorrectException;
import com.mjpancheri.authorizer.common.exception.handler.ExceptionHandlerController;
import com.mjpancheri.authorizer.common.util.CustomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class TransactionControllerTest {

  private static final String ENDPOINT_URL = "/transacoes";

  @Mock
  private TransactionService transactionService;
  @InjectMocks
  private TransactionController transactionController;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
        .setControllerAdvice(new ExceptionHandlerController()).build();
  }

  @Test
  @DisplayName("Cria uma nova transa????o quando o payload est?? correto")
  void createOk() throws Exception {
    var payload = TransactionBuilder.getCreateTransactionDto();
    when(transactionService.createTransaction(any(CreateTransactionDto.class))).thenReturn(CustomUtils.TRANSACTION_OK);

    mockMvc.perform(
            post(ENDPOINT_URL).contentType(MediaType.APPLICATION_JSON)
                .content(CustomUtils.asJsonString(payload))
        ).andExpect(status().isCreated())
        .andExpect(content().string(CustomUtils.TRANSACTION_OK));
    verify(transactionService, times(1))
        .createTransaction(any(CreateTransactionDto.class));
    verifyNoMoreInteractions(transactionService);
  }

  @Test
  @DisplayName("Retorna erro de valida????o quando n??mero do cart??o est?? com tamanho incorreto")
  void createIncorrectCardNumberValidation() throws Exception {
    var payload = TransactionBuilder.getCreateTransactionDtoNumberIncorrect();

    mockMvc.perform(
            post(ENDPOINT_URL).contentType(MediaType.APPLICATION_JSON)
                .content(CustomUtils.asJsonString(payload))
        ).andExpect(status().isBadRequest())
        .andExpect(content().string("numeroCartao: Deve conter 16 digitos; "));
    verify(transactionService, times(0))
        .createTransaction(any(CreateTransactionDto.class));
    verifyNoMoreInteractions(transactionService);
  }

  @Test
  @DisplayName("Retorna erro de valida????o quando n??mero do cart??o ?? inv??lido")
  void createInvalidCardNumberValidation() throws Exception {
    var payload = TransactionBuilder.getCreateTransactionDtoNumberInvalid();

    mockMvc.perform(
            post(ENDPOINT_URL).contentType(MediaType.APPLICATION_JSON)
                .content(CustomUtils.asJsonString(payload))
        ).andExpect(status().isBadRequest())
        .andExpect(content().string("numeroCartao: Somente n??meros; "));
    verify(transactionService, times(0))
        .createTransaction(any(CreateTransactionDto.class));
    verifyNoMoreInteractions(transactionService);
  }

  @Test
  @DisplayName("Retorna erro de valida????o quando senha do cart??o est?? com tamanho incorreto")
  void createIncorrectCardPasswordValidation() throws Exception {
    var payload = TransactionBuilder.getCreateTransactionDtoPasswordIncorrect();

    mockMvc.perform(
            post(ENDPOINT_URL).contentType(MediaType.APPLICATION_JSON)
                .content(CustomUtils.asJsonString(payload))
        ).andExpect(status().isBadRequest())
        .andExpect(content().string("senhaCartao: Deve conter 4 digitos; "));
    verify(transactionService, times(0))
        .createTransaction(any(CreateTransactionDto.class));
    verifyNoMoreInteractions(transactionService);
  }

  @Test
  @DisplayName("Retorna erro de valida????o quando senha do cart??o ?? inv??lida")
  void createInvalidCardPasswordValidation() throws Exception {
    var payload = TransactionBuilder.getCreateTransactionDtoPasswordInvalid();

    mockMvc.perform(
            post(ENDPOINT_URL).contentType(MediaType.APPLICATION_JSON)
                .content(CustomUtils.asJsonString(payload))
        ).andExpect(status().isBadRequest())
        .andExpect(content().string("senhaCartao: Somente n??meros; "));
    verify(transactionService, times(0))
        .createTransaction(any(CreateTransactionDto.class));
    verifyNoMoreInteractions(transactionService);
  }

  @Test
  @DisplayName("Retorna erro de valida????o quando valor n??o for informado")
  void createWithoutAmountValidation() throws Exception {
    var payload = TransactionBuilder.getCreateTransactionDtoWithoutAmount();

    mockMvc.perform(
            post(ENDPOINT_URL).contentType(MediaType.APPLICATION_JSON)
                .content(CustomUtils.asJsonString(payload))
        ).andExpect(status().isBadRequest())
        .andExpect(content().string("valor: Valor obrigat??rio; "));
    verify(transactionService, times(0))
        .createTransaction(any(CreateTransactionDto.class));
    verifyNoMoreInteractions(transactionService);
  }

  @Test
  @DisplayName("Retorna erro de transa????o quando o cart??o n??o for encontrado")
  void createIncorrectCardNumber() throws Exception {
    var payload = TransactionBuilder.getCreateTransactionDto();
    when(transactionService.createTransaction(any(CreateTransactionDto.class))).thenThrow(
        CardIncorrectException.class);

    mockMvc.perform(
            post(ENDPOINT_URL).contentType(MediaType.APPLICATION_JSON)
                .content(CustomUtils.asJsonString(payload))
        ).andExpect(status().isUnprocessableEntity())
        .andExpect(content().string(CustomUtils.TRANSACTION_INCORRECT_CARD));
    verify(transactionService, times(1))
        .createTransaction(any(CreateTransactionDto.class));
    verifyNoMoreInteractions(transactionService);
  }

  @Test
  @DisplayName("Retorna erro de transa????o quando a senha estiver incorreta")
  void createIncorrectCardPassword() throws Exception {
    var payload = TransactionBuilder.getCreateTransactionDto();
    when(transactionService.createTransaction(any(CreateTransactionDto.class))).thenThrow(
        PasswordIncorrectException.class);

    mockMvc.perform(
            post(ENDPOINT_URL).contentType(MediaType.APPLICATION_JSON)
                .content(CustomUtils.asJsonString(payload))
        ).andExpect(status().isUnprocessableEntity())
        .andExpect(content().string(CustomUtils.TRANSACTION_INCORRECT_PASSWORD));
    verify(transactionService, times(1))
        .createTransaction(any(CreateTransactionDto.class));
    verifyNoMoreInteractions(transactionService);
  }

  @Test
  @DisplayName("Retorna erro de transa????o quando a saldo for insuficiente")
  void createInsufficientBalance() throws Exception {
    var payload = TransactionBuilder.getCreateTransactionDto();
    when(transactionService.createTransaction(any(CreateTransactionDto.class))).thenThrow(
        InsufficientBalanceException.class);

    mockMvc.perform(
            post(ENDPOINT_URL).contentType(MediaType.APPLICATION_JSON)
                .content(CustomUtils.asJsonString(payload))
        ).andExpect(status().isUnprocessableEntity())
        .andExpect(content().string(CustomUtils.TRANSACTION_INSUFFICIENT_BALANCE));
    verify(transactionService, times(1))
        .createTransaction(any(CreateTransactionDto.class));
    verifyNoMoreInteractions(transactionService);
  }
}
