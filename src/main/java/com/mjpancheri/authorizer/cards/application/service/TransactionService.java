package com.mjpancheri.authorizer.cards.application.service;

import com.mjpancheri.authorizer.cards.application.rest.dto.CreateTransactionDto;
import com.mjpancheri.authorizer.cards.domain.repository.TransactionRepository;
import com.mjpancheri.authorizer.cards.infrastructure.mapper.TransactionMapper;
import com.mjpancheri.authorizer.common.exception.InsufficientBalanceException;
import com.mjpancheri.authorizer.common.exception.PasswordIncorrectException;
import com.mjpancheri.authorizer.common.util.CustomUtils;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final CardService cardService;

  public synchronized String createTransaction(CreateTransactionDto createTransactionDto) {
    var card = cardService.getCard(createTransactionDto.getNumeroCartao());

    if (!cardService.validCardPassword(card, createTransactionDto.getSenhaCartao())) {
      throw new PasswordIncorrectException();
    }

    if (card.getBalance().compareTo(createTransactionDto.getValor()) < 0) {
      throw new InsufficientBalanceException();
    }

    var transaction = TransactionMapper.convertToTransaction(createTransactionDto.getValor(), card);
    transactionRepository.save(transaction);
    cardService.updateCardBalance(card, createTransactionDto.getValor());

    return CustomUtils.TRANSACTION_OK;
  }
}
